#coding:utf-8
import tornado.ioloop
import tornado.web
import json
import pymysql
import urllib
import urllib.request

class GetVoucherHandler(tornado.web.RequestHandler):

    def post(self, *args, **kwargs):
        data = json.loads(self.request.body)
        orderId = data["orderId"]
        type = data["type"]
        queryVoucher = self.fetchVoucherByOrderId(orderId)

        if(queryVoucher == None):
            orderResult = self.queryOrderByIdAndType(orderId,type)
            order = orderResult['order']

            # jsonStr = json.dumps(orderResult)
            # self.write(jsonStr)

            config = {
                'host':'ts-voucher-mysql',
                'port':3306,
                'user':'root',
                'password':'root',
                'db':'voucherservice'
            }
            conn = pymysql.connect(**config)
            cur = conn.cursor()
            sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
            try:
                cur.execute(sql,(order['id'],order['travelDate'],order['travelTime'],order['contactsName'],order['trainNumber'],order['seatClass'],order['seatNumber'],order['from'],order['to'],order['price']))
                conn.commit()
            finally:
                conn.close()
            self.write(self.fetchVoucherByOrderId(orderId))
        else:
            self.write(queryVoucher)

    def queryOrderByIdAndType(self,orderId,type):
        type = int(type)
        if(type == 0):
            url='http://ts-order-other-service:12032/order/getById'
        else:
            url='http://ts-order-service:12031/order/getById'
        values ={'orderId':orderId}
        jdata = json.dumps(values).encode(encoding='utf-8')
        header_dict = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko',"Content-Type": "application/json"}
        req = urllib.request.Request(url=url,data=jdata,headers=header_dict)
        response = urllib.request.urlopen(req)
        return json.loads(response.read())

    def fetchVoucherByOrderId(self,orderId):
        config = {
            'host':'ts-voucher-mysql',
            'port':3306,
            'user':'root',
            'password':'root',
            'db':'voucherservice'
        }
        conn = pymysql.connect(**config)
        cur = conn.cursor()
        sql = 'SELECT * FROM voucher where order_id = %s'
        try:
            cur.execute(sql,(orderId))
            voucher = cur.fetchone()
            conn.commit()
            if(cur.rowcount < 1):
                return None
            else:
                voucherData = {}
                voucherData['voucher_id'] = voucher[0]
                voucherData['order_id'] = voucher[1]
                voucherData['travelDate'] = voucher[2]
                voucherData['contactName'] = voucher[4]
                voucherData['train_number'] = voucher[5]
                voucherData['seat_number'] = voucher[7]
                voucherData['start_station'] = voucher[8]
                voucherData['dest_station'] = voucher[9]
                voucherData['price'] = voucher[10]
                jsonStr = json.dumps(voucherData)
                print(jsonStr)
                return jsonStr
        finally:
            conn.close()

    def get(self):
        config = {
            'host':'ts-voucher-mysql',
            'port':3306,
            'user':'root',
            'password':'root',
            'db':'voucherservice'
        }
        conn = pymysql.connect(**config)
        cur = conn.cursor()
        sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
        try:
            cur.execute(sql,('1',"2017-10-18","10:58:00","lab401","D3301",1,"31","ShangHai","BeiJing",379))
            conn.commit()
        finally:
            pass

        self.write(self.fetchVoucherByOrderId('5ad7750b-a68b-49c0-a8c0-32776b067703'))

class TestHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("testing")

class Test1Handler(tornado.web.RequestHandler):
    def get(self):
        self.write("testing ------ 2")

def make_app():
    return tornado.web.Application([
        (r"/getVoucher", GetVoucherHandler),
        (r"/test", TestHandler),
        (r"/test1", Test1Handler),
    ])

def initDatabase():
    config = {
        'host':'ts-voucher-mysql',
        'port':3306,
        'user':'root',
        'password':'root'
    }
    connect = pymysql.connect(**config)
    cur = connect.cursor()

    sql = "CREATE SCHEMA IF NOT EXISTS `voucherservice` ; "
    try:
        cur.execute(sql)
        connect.commit()
    finally:
        pass

    sql = "use voucherservice;CREATE TABLE if not exists `voucherservice`.`voucher` (`voucher_id` INT NOT NULL AUTO_INCREMENT,`order_id` VARCHAR(1024) NOT NULL,`travelDate` VARCHAR(1024) NOT NULL,`travelTime` VARCHAR(1024) NOT NULL,`contactName` VARCHAR(1024) NOT NULL,`trainNumber` VARCHAR(1024) NOT NULL,`seatClass` INT NOT NULL,`seatNumber` VARCHAR(1024) NOT NULL,`startStation` VARCHAR(1024) NOT NULL,`destStation` VARCHAR(1024) NOT NULL,`price` FLOAT NOT NULL,PRIMARY KEY (`voucher_id`));"
    try:
        cur.execute(sql)
        connect.commit()
    finally:
        connect.close()

if __name__ == "__main__":

    initDatabase()
    app = make_app()
    app.listen(16101)
    tornado.ioloop.IOLoop.current().start()


    