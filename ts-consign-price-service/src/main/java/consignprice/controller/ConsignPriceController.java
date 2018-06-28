package consignprice.controller;

import consignprice.domain.GetPriceDomain;
import consignprice.domain.Information;
import consignprice.domain.PriceConfig;
import consignprice.service.ConsignPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Random;

@RestController
public class ConsignPriceController {
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsignPriceController.class);

    @Autowired
    ConsignPriceService service;

    @RequestMapping(value = "/consignPrice/getPrice", method= RequestMethod.POST)
    public double getPriceByWeightAndRegion(@RequestBody GetPriceDomain info){
        return service.getPriceByWeightAndRegion(info);
    }

    @RequestMapping(value = "/consignPrice/getPriceInfo", method= RequestMethod.GET)
    public String getPriceInfo(){
        return service.queryPriceInformation();
    }

    @RequestMapping(value = "/consignPrice/getPriceConfig", method= RequestMethod.GET)
    public PriceConfig getPriceConfig(){
        return service.getPriceConfig();
    }

    @RequestMapping(value = "/consignPrice/modifyPriceConfig", method= RequestMethod.POST)
    public boolean modifyPriceConfig(@RequestBody PriceConfig priceConfig){
        return service.createAndModifyPrice(priceConfig);
    }

    @RequestMapping(value = "/consignPrice/getDrawbackPercent", method= RequestMethod.POST)
    public double getDrawbackPercent(@RequestBody Information information){
        double result = service.getDrawbackPercent(information);
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
            logger.info("[VM:vm1][Service:ts-consign-price-service]" + "[DrawbackPercent:" + result + "]");
        }else if(random < 0.66){
            boundedDouble = min + new Random().nextDouble() * (max - min);
            temp = df.format(boundedDouble);
            boundedDouble = Double.parseDouble(temp);
            result = result + boundedDouble;
            logger.info("[VM:vm2][Service:ts-consign-price-service]" + "[DrawbackPercent:" + result + "]");
        }else{
            boundedDouble = min + new Random().nextDouble() * (max - min);
            temp = df.format(boundedDouble);
            boundedDouble = Double.parseDouble(temp);
            result = result + boundedDouble;
            logger.info("[VM:vm3][Service:ts-consign-price-service]" + "[DrawbackPercent:" + result + "]");
        }
        return result;
    }
}
