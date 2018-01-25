package click.async;

import click.domain.OrderTicketsInfo;
import click.domain.OrderTicketsInfoPlus;
import click.domain.OrderTicketsResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Future;

@Component  
public class AsyncTask {
    @Autowired
	private RestTemplate restTemplate;

    @Async("myAsync")
    public Future<OrderTicketsResult> sendAsyncClickTwice(OrderTicketsInfo info,String loginId, String loginToken) throws InterruptedException {

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//                .create();
//        String jsonString = gson.toJson(info);
//
//        System.out.println("【发送的Json】" + jsonString);
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Cookie","loginId=" + loginId);
//        requestHeaders.add("Cookie","loginId=" + loginToken);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<OrderTicketsInfo> entity = new HttpEntity<>(info,headers);
//        ResponseEntity<OrderTicketsResult> resp = restTemplate.exchange(
//                "http://ts-preserve-service:14568/preserve", HttpMethod.POST, entity, OrderTicketsResult.class);
//        OrderTicketsResult result = resp.getBody();

        OrderTicketsInfoPlus plus = new OrderTicketsInfoPlus();
        plus.setInfo(info);
        plus.setLoginId(loginId);
        plus.setLoginToken(loginToken);

        OrderTicketsResult result = restTemplate.postForObject("http://ts-preserve-service:14568/preserve",plus,OrderTicketsResult.class);

        return new AsyncResult<>(result);

    }

      
}  
