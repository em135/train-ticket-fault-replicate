package other.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import other.domain.CheckValidResult;
import other.domain.QueryStationById;

import java.util.concurrent.Future;

@Component  
public class AsyncTask {
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("myAsync")
    public Future<CheckValidResult> checkStationValid(QueryStationById info) throws InterruptedException{

        System.out.println("[Order Other Service][Check Station Valid]");
        CheckValidResult result = restTemplate.postForObject(
                "http://ts-station-service:12345/station/checkValid",
                info,CheckValidResult.class);
        return new AsyncResult<>(result);

    }
}  
