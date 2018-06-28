package foodsearch.controller;

import foodsearch.domain.*;
import foodsearch.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

@RestController
public class FoodController {
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FoodController.class);

    @Autowired
    FoodService foodService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Food Service ] !";
    }

    @RequestMapping(path = "/food/getFood", method = RequestMethod.POST)
    public GetAllFoodOfTripResult getFood(@RequestBody GetAllFoodOfTripInfo gati){
        System.out.println("[Food Service]Get the Get Food Request!");
        return foodService.getAllFood(gati.getDate(), gati.getStartStation(), gati.getEndStation(), gati.getTripId());
    }

    @RequestMapping(path = "/food/createFoodOrder", method = RequestMethod.POST)
    public AddFoodOrderResult createFoodOrder(@RequestBody AddFoodOrderInfo afoi){
        System.out.println("[Food Service]Try to Create a FoodOrder!");
        return foodService.createFoodOrder(afoi);
    }

    @RequestMapping(path = "/food/cancelFoodOrder", method = RequestMethod.POST)
    public CancelFoodOrderResult cancelFoodOrder(@RequestBody CancelFoodOrderInfo cfoi){
        System.out.println("[Food Service]Try to Cancel a FoodOrder!");
        return foodService.cancelFoodOrder(cfoi);
    }

    @RequestMapping(path = "/food/updateFoodOrder", method = RequestMethod.POST)
    public UpdateFoodOrderResult updateFoodOrder(@RequestBody UpdateFoodOrderInfo ufoi){
        System.out.println("[Food Service]Try to Update a FoodOrder!");
        return foodService.updateFoodOrder(ufoi);
    }

    @RequestMapping(path = "/food/findAllFoodOrder", method = RequestMethod.GET)
    public List<FoodOrder> findAllFoodOrder(){
        System.out.println("[Food Service]Try to Find all FoodOrder!");
        return foodService.findAllFoodOrder();
    }

    @RequestMapping(path = "/food/findFoodOrderByOrderId", method = RequestMethod.POST)
    public FindByOrderIdResult findFoodOrderByOrderId(@RequestBody FindByOrderIdInfo foi){
        System.out.println("[Food Service]Try to Find all FoodOrder!");
        return foodService.findByOrderId(foi.getOrderId());
    }

    @RequestMapping(path = "/food/getDrawbackPercent", method = RequestMethod.POST)
    public double getDrawbackPercent(@RequestBody Information info){
        double result = foodService.getDrawbackPercent(info);
//        logger.info("[Service:ts-food-service]" + "[DrawbackPercent:" + result + "]");
        DecimalFormat df = new DecimalFormat("#.00");
        double min = 0.1;
        double max = 0.3;
        double boundedDouble;
        String temp;
        double random = Math.random();
        if(random < 0.33 ){
            boundedDouble = min + new Random().nextDouble() * (max - min);
            temp = df.format(boundedDouble);
            boundedDouble = Double.parseDouble(temp);
            result = result + boundedDouble;
            temp = df.format(result);
            result = Double.parseDouble(temp);
            logger.info("[VM:vm1][Service:ts-food-service]" + "[DrawbackPercent:" + result + "]");
        }else if(random < 0.66){
            boundedDouble = min + new Random().nextDouble() * (max - min);
            temp = df.format(boundedDouble);
            boundedDouble = Double.parseDouble(temp);
            result = result + boundedDouble;
            temp = df.format(result);
            result = Double.parseDouble(temp);
            logger.info("[VM:vm2][Service:ts-food-service]" + "[DrawbackPercent:" + result + "]");
        }else{
            boundedDouble = min + new Random().nextDouble() * (max - min);
            temp = df.format(boundedDouble);
            boundedDouble = Double.parseDouble(temp);
            result = result + boundedDouble;
            temp = df.format(result);
            result = Double.parseDouble(temp);
            logger.info("[VM:vm3][Service:ts-food-service]" + "[DrawbackPercent:" + result + "]");
        }
        return result;
    }

}
