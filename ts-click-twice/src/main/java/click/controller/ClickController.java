package click.controller;

import click.async.AsyncTask;
import click.domain.LoginInfo;
import click.domain.LoginResult;
import click.domain.OrderTicketsResult;
import click.domain.TravelAdvanceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Random;
import java.util.concurrent.Future;

@RestController
public class ClickController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to auto ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/click/autoSuccess", method = RequestMethod.GET)
    public boolean autoSuccess() throws Exception{
        //登录
        String email = "fdse_microservices@163.com";
        String password = "DefaultPassword";
        LoginInfo loginInfo = new LoginInfo(
                email,
                password,
                "abcd");
        LoginResult loginResult = restTemplate.postForObject(
                "http://ts-login-service:12342/login",
                loginInfo,LoginResult.class
        );
        String loginId = loginResult.getAccount().getId().toString();
        String loginToken = loginResult.getToken();

        //点三次确认车票，并等待结果
        int numReserve = new Random().nextInt(5);
        for(int i = 0; i < 3; i++){
            System.out.println("[订票] " + i + " " + numReserve);
            Future<OrderTicketsResult> taskResult = asyncTask.sendOrderTicket(loginId,loginToken);
            try{
                OrderTicketsResult orderTicketsResult = taskResult.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        Future<OrderTicketsResult> taskResult2 = asyncTask.sendOrderTicket(loginId,loginToken);
//        try{
//            OrderTicketsResult orderTicketsResult2 = taskResult2.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Future<OrderTicketsResult> taskResult3 = asyncTask.sendOrderTicket(loginId,loginToken);
//        try{
//            OrderTicketsResult orderTicketsResult3 = taskResult3.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        int numReserch = new Random().nextInt(5);
        for(int i = 0;i < 2;i++){

            System.out.println("[搜索] " + i + " " + numReserch);
            Future<TravelAdvanceResult> taskResult = asyncTask.searchInAdvanceSearch(loginId,loginToken);
            try{
                TravelAdvanceResult orderTicketsResult = taskResult.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //去高级查询里点击两次车票
//
//        Future<RoutePlanResults> taskResult5 = asyncTask.searchInAdvanceSearch(loginId,loginToken);
//        try{
//            RoutePlanResults orderTicketsResult5 = taskResult5.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        return true;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/click/autoFail", method = RequestMethod.GET)
    public boolean autoFail() throws Exception{
        //登录
        String email = "fdse_microservices@163.com";
        String password = "DefaultPassword";
        LoginInfo loginInfo = new LoginInfo(
                email,
                password,
                "abcd");
        LoginResult loginResult = restTemplate.postForObject(
                "http://ts-login-service:12342/login",
                loginInfo,LoginResult.class
        );
        String loginId = loginResult.getAccount().getId().toString();
        String loginToken = loginResult.getToken();

        //点三次确认车票，并等待结果
        int numReserve = new Random().nextInt(5);
        for(int i = 0;i < numReserve;i++){
            Future<OrderTicketsResult> taskResult = asyncTask.sendOrderTicket(loginId,loginToken);
//            try{
//                OrderTicketsResult orderTicketsResult = taskResult.get();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }

//        Future<OrderTicketsResult> taskResult2 = asyncTask.sendOrderTicket(loginId,loginToken);
//        try{
//            OrderTicketsResult orderTicketsResult2 = taskResult2.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Future<OrderTicketsResult> taskResult3 = asyncTask.sendOrderTicket(loginId,loginToken);
//        try{
//            OrderTicketsResult orderTicketsResult3 = taskResult3.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        int numReserch = new Random().nextInt(5);
        for(int i = 0;i < numReserch;i++){
            Future<TravelAdvanceResult> taskResult = asyncTask.searchInAdvanceSearch(loginId,loginToken);
//            try{
//                RoutePlanResults orderTicketsResult = taskResult.get();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
        //去高级查询里点击两次车票
//
//        Future<RoutePlanResults> taskResult5 = asyncTask.searchInAdvanceSearch(loginId,loginToken);
//        try{
//            RoutePlanResults orderTicketsResult5 = taskResult5.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        return false;
    }

}
