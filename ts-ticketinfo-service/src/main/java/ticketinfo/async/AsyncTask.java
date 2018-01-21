package ticketinfo.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;

import java.util.concurrent.Future;

@Component  
public class AsyncTask {  

    @Autowired
	private RestTemplate restTemplate;

    @Async("mySimpleAsync")
    public Future<ResultForTravel> queryForTravel(QueryForTravel info){
        ResultForTravel result = restTemplate.postForObject(
                "http://ts-basic-service:15680/basic/queryForTravel", info, ResultForTravel.class);
        return new AsyncResult<>(result);
    }

}  
