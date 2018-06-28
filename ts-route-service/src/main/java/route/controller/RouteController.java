package route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import route.domain.*;
import route.service.RouteService;

import java.text.DecimalFormat;
import java.util.Random;

@RestController
public class RouteController {
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Route Service ] !";
    }

    @RequestMapping(path = "/route/createAndModify", method = RequestMethod.POST)
    public CreateAndModifyRouteResult createAndModifyRoute(@RequestBody CreateAndModifyRouteInfo createAndModifyRouteInfo){
        return routeService.createAndModify(createAndModifyRouteInfo);
    }

    @RequestMapping(path = "/route/delete", method = RequestMethod.POST)
    public DeleteRouteResult deleteRoute(@RequestBody DeleteRouteInfo deleteRouteInfo){
        return routeService.deleteRoute(deleteRouteInfo);
    }

    @RequestMapping(path = "/route/queryById/{routeId}", method = RequestMethod.GET)
    public GetRouteByIdResult queryById(@PathVariable String routeId){
        return routeService.getRouteById(routeId);
    }

    @RequestMapping(path = "/route/queryAll", method = RequestMethod.GET)
    public GetRoutesListlResult queryAll(){
        return routeService.getAllRoutes();
    }

    @RequestMapping(path = "/route/queryByStartAndTerminal", method = RequestMethod.POST)
    public GetRoutesListlResult queryByStartAndTerminal(@RequestBody GetRouteByStartAndTerminalInfo getRouteByStartAndTerminalInfo){
        return routeService.getRouteByStartAndTerminal(getRouteByStartAndTerminalInfo);
    }

    @RequestMapping(path = "/route/getDrawbackPercent", method = RequestMethod.POST)
    public double getDrawbackPercent(@RequestBody Information information){
        double result = routeService.getDrawbackPercent(information);
//        logger.info("[Service:ts-route-service]" + "[DrawbackPercent:" + result + "]");
//        DecimalFormat df = new DecimalFormat("#.00");
//        double min = 0.1;
//        double max = 0.3;
//        double boundedDouble = min + new Random().nextDouble() * (max - min);
//        String temp = df.format(boundedDouble);
//        boundedDouble = Double.parseDouble(temp);
//        logger.info("[VM:vm1][Service:ts-route-service]" + "[DrawbackPercent:" + (result + boundedDouble) + "]");
//        boundedDouble = min + new Random().nextDouble() * (max - min);
//        temp = df.format(boundedDouble);
//        boundedDouble = Double.parseDouble(temp);
//        logger.info("[VM:vm2][Service:ts-route-service]" + "[DrawbackPercent:" + (result + boundedDouble) + "]");
//        boundedDouble = min + new Random().nextDouble() * (max - min);
//        temp = df.format(boundedDouble);
//        boundedDouble = Double.parseDouble(temp);
//        logger.info("[VM:vm3][Service:ts-route-service]" + "[DrawbackPercent:" + (result + boundedDouble) + "]");
        return result;
    }

}