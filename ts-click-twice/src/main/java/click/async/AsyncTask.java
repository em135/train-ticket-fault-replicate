package click.async;

import click.domain.OrderTicketsInfo;
import click.domain.OrderTicketsResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginId=" + loginToken);

        Gson gson = new Gson();
        String jsonString = gson.toJson(info);

        HttpEntity requestEntity = new HttpEntity(jsonString,requestHeaders);
        ResponseEntity rssResponse = restTemplate.exchange(
                "http://ts-preserve-service:14568/preserve",
                HttpMethod.POST,
                requestEntity,
                OrderTicketsResult.class
        );
        OrderTicketsResult result = (OrderTicketsResult) rssResponse.getBody();

        return new AsyncResult<>(result);

    }

      
}  
