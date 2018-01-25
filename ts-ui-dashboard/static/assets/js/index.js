/**
 * Created by lwh on 2017/11/16.
 */
/*
 * 显示管理员名字
 * */
var loadBody = function () {
    var username = sessionStorage.getItem("admin_name");
    if (username == null) {
        alert("Please login first!");
        location.href = "adminlogin.html";
    }
    else {
        document.getElementById("admin_name").innerHTML = username;
    }
};

$("#order_search_by_station_search_lock").click(function(){
    var fromStationId = $('#order_search_by_station_from').val();
    var toStationId = $('#order_search_by_station_to').val();
    $.ajax({
        type: "get",
        url: "/adminOrder/suspendOrder/" + fromStationId + "/" + toStationId,
        contentType: "application/json",
        dataType: "json",
        async:false,
        xhrFields: {
            withCredentials: true
        },
        success: function(){
            alert("Lock Success");
        },
        error: function () {
            alert("Lock Fail")
        }
    });
    //然后再刷新搜索结果


});

$("#order_search_by_station_unlock").click(function(){
    var fromStationId = $('#order_search_by_station_from').val();
    var toStationId = $('#order_search_by_station_to').val();
    $.ajax({
        type: "get",
        url: "/adminOrder/cancelSuspendOrder/" + fromStationId + "/" + toStationId,
        contentType: "application/json",
        dataType: "json",
        async:false,
        xhrFields: {
            withCredentials: true
        },
        success: function(){
            alert("Unlock Success");
        },
        error: function () {
            alert("Unlock Fail");
        }
    });
});

/*
 * 登出
 * */
var logout = function () {
    sessionStorage.clear();
    location.href = "adminlogin.html";
}

/*
 * 将加载数据封装为一个服务
 * */
var app = angular.module('myApp', []);
app.factory('loadDataService', function ($http, $q) {

    var service = {};

    //获取并返回数据
    service.loadRecordList = function (param) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "get",
            url: "/adminorder/findAll/" + param.id,
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            if (data.status) {
                information.orderRecords = data.orders;
                deferred.resolve(information);
            }
            else{
                alert("Request the order list fail!" + data.message);
            }
        });

        return promise;
    };

    return service;
});

/*
 * 加载列表
 * */
app.controller('indexCtrl', function ($scope, $http,$window,loadDataService) {
    var param = {};
    param.id = sessionStorage.getItem("admin_id");

    //刷新页面
    $scope.reloadRoute = function () {
        $window.location.reload();
    };

    //首次加载显示数据
    loadDataService.loadRecordList(param).then(function (result) {
        $scope.records = result.orderRecords;
        //$scope.decodeInfo(result.orderRecords[0]);
    });

    $scope.decodeInfo = function (obj) {
        var des = "";
        for(var name in obj){
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }
    
    //Add new order
    $scope.addNewOrder = function () {
        $('#add_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/adminorder/addOrder",
                    withCredentials: true,
                    data:{
                        loginid: sessionStorage.getItem("admin_id"),
                        order:{
                            boughtDate: $scope.add_order_bought_date,
                            travelDate: $scope.add_order_travel_date,
                            travelTime: $scope.add_order_travel_time,
                            accountId: $scope.add_order_account,
                            contactsName: $scope.add_order_passenger,
                            documentType: $scope.add_order_document_type,
                            contactsDocumentNumber: $scope.add_order_document_number,
                            trainNumber: $scope.add_order_train_number,
                            coachNumber: $scope.add_order_coach_number,
                            seatClass: $scope.add_order_seat_class,
                            seatNumber: $scope.add_order_seat_number,
                            from: $scope.add_order_from,
                            to: $scope.add_order_to,
                            status: $scope.add_order_status,
                            price: $scope.add_order_price
                        }
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Request the order list fail!" + data.message);
                    }
                });
            },
            onCancel: function(e) {
                alert('You have canceled the operation!');
            }
        });
    }
    
    //Update exist order
    $scope.updateOrder = function (record) {

        // //发送OrderId请求
        // $.ajax({
        //     type: "get",
        //     url: "/adminOrder/suspendOrder/" + record.id,
        //     contentType: "application/json",
        //     dataType: "json",
        //     async:false,
        //     xhrFields: {
        //         withCredentials: true
        //     },
        //     success: function(result){
        //         alert("已锁定Order");
        //     },
        //     complete: function(){
        //     }
        // });



        $scope.update_order_id = record.id;
        $scope.update_order_bought_date = record.boughtDate;
        $scope.update_order_travel_date = record.travelDate;
        $scope.update_order_travel_time = record.travelTime;
        $scope.update_order_account = record.accountId;
        $scope.update_order_passenger = record.contactsName;
        $scope.update_add_order_document_type = record.documentType;
        $scope.update_order_document_number = record.contactsDocumentNumber;
        $scope.update_order_train_number = record.trainNumber;
        $scope.update_order_coach_number = record.coachNumber;
        $scope.update_order_seat_class = record.seatClass;
        $scope.update_order_seat_number = record.seatNumber;
        $scope.update_order_from = record.from;
        $scope.update_order_to = record.to;
        $scope.update_order_status = record.status;
        $scope.update_order_price = record.price;

        $('#update_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/adminorder/updateOrder",
                    withCredentials: true,
                    data:{
                        loginid: sessionStorage.getItem("admin_id"),
                        order:{
                            id: $scope.update_order_id,
                            boughtDate: $scope.update_order_bought_date,
                            travelDate: $scope.update_order_travel_date,
                            travelTime: $scope.update_order_travel_time,
                            accountId: $scope.update_order_account,
                            contactsName: $scope.update_order_passenger,
                            documentType: $scope.update_add_order_document_type,
                            contactsDocumentNumber: $scope.update_order_document_number,
                            trainNumber: $scope.update_order_train_number,
                            coachNumber: $scope.update_order_coach_number,
                            seatClass: $scope.update_order_seat_class,
                            seatNumber: $scope.update_order_seat_number,
                            from: $scope.update_order_from,
                            to: $scope.update_order_to,
                            status: $scope.update_order_status,
                            price: $scope.update_order_price
                        }
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Request the order list fail!" + data.message);
                    }
                });
            },
            onCancel: function(e) {

                // //发送取消操作的请求
                // //发送OrderId请求
                // $.ajax({
                //     type: "get",
                //     url: "/adminOrder/cancelSuspendOrder/" + record.id,
                //     contentType: "application/json",
                //     dataType: "json",
                //     async:false,
                //     xhrFields: {
                //         withCredentials: true
                //     },
                //     success: function(result){
                //         alert("已解锁该Order");
                //     },
                //     complete: function(){
                //     }
                // });

                alert('You have canceled the operation!');
            }
        });
    }

    //Delete order
    $scope.deleteOrder = function(orderId,trainNumber){
        $('#delete_confirm').modal({
            relatedTarget: this,
            onConfirm: function(options) {
                $http({
                    method: "post",
                    url: "/adminorder/deleteOrder",
                    withCredentials: true,
                    data: {
                        loginid: sessionStorage.getItem("admin_id"),
                        orderId: orderId,
                        trainNumber: trainNumber
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Request the order list fail!" + data.message);
                    }
                });
            },
            // closeOnConfirm: false,
            onCancel: function() {
                alert('You have canceled the operation!');
            }
        });
    }
});