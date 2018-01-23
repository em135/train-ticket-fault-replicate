package other.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import other.domain.*;
import other.service.OrderOtherService;
import java.util.ArrayList;

@RestController
public class OrderOtherController {

    @Autowired
    private OrderOtherService orderService;

    @Autowired
    private RestTemplate restTemplate;

    public static int onProcessingRequestsNumber = 0;


    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Other Service ] !";
    }

    /***************************For Normal Use***************************/

    @RequestMapping(value="/orderOther/getTicketListByDateAndTripId", method = RequestMethod.POST)
    public LeftTicketInfo getTicketListByDateAndTripId(@RequestBody SeatRequest seatRequest){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Get Sold Ticket] Date:" + seatRequest.getTravelDate().toString());
        LeftTicketInfo leftTicketInfo = orderService.getSoldTickets(seatRequest);;

        onProcessingRequestsNumber--;

        return leftTicketInfo;

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/create", method = RequestMethod.POST)
    public CreateOrderResult createNewOrder(@RequestBody CreateOrderInfo coi){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Create Order] Create Order form " + coi.getOrder().getFrom() + " --->"
                + coi.getOrder().getTo() + " at " + coi.getOrder().getTravelDate());
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        CreateOrderResult createOrderResult;
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            createOrderResult = orderService.create(coi.getOrder());
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            CreateOrderResult cor = new CreateOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            createOrderResult = cor;
        }

        onProcessingRequestsNumber--;

        return createOrderResult;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/adminAddOrder", method = RequestMethod.POST)
    public AddOrderResult addcreateNewOrder(@RequestBody Order order){

        onProcessingRequestsNumber++;

        AddOrderResult addOrderResult = orderService.addNewOrder(order);

        onProcessingRequestsNumber--;

        return addOrderResult;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/query", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi,@CookieValue String loginId,@CookieValue String loginToken){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Query Orders] Query Orders for " + loginId);
        VerifyResult tokenResult = verifySsoLogin(loginToken);
        ArrayList<Order> orders;
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            orders = orderService.queryOrders(qi,loginId);
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            orders = new ArrayList<>();
        }

        onProcessingRequestsNumber--;

        return orders;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/calculate", method = RequestMethod.POST)
    public CalculateSoldTicketResult calculateSoldTicket(@RequestBody CalculateSoldTicketInfo csti){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Calculate Sold Tickets] Date:" + csti.getTravelDate() + " TrainNumber:"
                + csti.getTrainNumber());
        CalculateSoldTicketResult result = orderService.queryAlreadySoldOrders(csti);

        onProcessingRequestsNumber--;
        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/price", method = RequestMethod.POST)
    public GetOrderPriceResult getOrderPrice(@RequestBody GetOrderPrice info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Get Order Price] Order Id:" + info.getOrderId());
        GetOrderPriceResult result = orderService.getOrderPrice(info);

        onProcessingRequestsNumber--;

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/payOrder", method = RequestMethod.POST)
    public PayOrderResult payOrder(@RequestBody PayOrderInfo info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Pay Order] Order Id:" + info.getOrderId());
        PayOrderResult result = orderService.payOrder(info);

        onProcessingRequestsNumber--;

        return result;

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/getById", method = RequestMethod.POST)
    public GetOrderResult getOrderById(@RequestBody GetOrderByIdInfo info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Get Order By Id] Order Id:" + info.getOrderId());
        GetOrderResult result = orderService.getOrderById(info);

        onProcessingRequestsNumber--;

        return result;

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/modifyOrderStatus", method = RequestMethod.POST)
    public ModifyOrderStatusResult modifyOrder(@RequestBody ModifyOrderStatusInfo info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Modify Order Status] Order Id:" + info.getOrderId());
        ModifyOrderStatusResult result = orderService.modifyOrder(info);

        onProcessingRequestsNumber--;

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/getOrderOtherInfoForSecurity", method = RequestMethod.POST)
    public GetOrderInfoForSecurityResult securityInfoCheck(@RequestBody GetOrderInfoForSecurity info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Security Info Get]");
        GetOrderInfoForSecurityResult result = orderService.checkSecurityAboutOrder(info);

        onProcessingRequestsNumber--;

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/update", method = RequestMethod.POST)
    public ChangeOrderResult saveOrderInfo(@RequestBody ChangeOrderInfo orderInfo){

        onProcessingRequestsNumber++;

        ChangeOrderResult result;
        VerifyResult tokenResult = verifySsoLogin(orderInfo.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            result =  orderService.saveChanges(orderInfo.getOrder());
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            ChangeOrderResult cor = new ChangeOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            result = cor;
        }

        onProcessingRequestsNumber--;

        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/adminUpdate", method = RequestMethod.POST)
    public UpdateOrderResult updateOrder(@RequestBody Order order){

        onProcessingRequestsNumber++
        ;
        UpdateOrderResult result = orderService.updateOrder(order);

        onProcessingRequestsNumber--;
        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/delete",method = RequestMethod.POST)
    public DeleteOrderResult deleteOrder(@RequestBody DeleteOrderInfo info){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Delete Order] Order Id:" + info.getOrderId());
        DeleteOrderResult result = orderService.deleteOrder(info);

        onProcessingRequestsNumber--;

        return result;
    }


    /***************For super admin(Single Service Test*******************/

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/findAll", method = RequestMethod.GET)
    public QueryOrderResult findAllOrder(){

        onProcessingRequestsNumber++;

        System.out.println("[Order Other Service][Find All Order]");
        QueryOrderResult result = orderService.getAllOrders();

        onProcessingRequestsNumber--;

        return result;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[Order Other Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }
}
