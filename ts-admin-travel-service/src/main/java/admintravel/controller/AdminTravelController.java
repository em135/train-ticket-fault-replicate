package admintravel.controller;

import admintravel.domain.request.AddAndModifyTravelRequest;
import admintravel.domain.request.DeleteTravelRequest;
import admintravel.domain.response.AdminFindAllResult;
import admintravel.domain.response.ResponseBean;
import admintravel.service.AdminTravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
public class AdminTravelController {
    @Autowired
    AdminTravelService adminTravelService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/admintravel/findAll/{id}", method = RequestMethod.GET)
    public AdminFindAllResult getAllTravels(@PathVariable String id){
        return adminTravelService.getAllTravels(id);
    }

    @RequestMapping(value = "/admintravel/addTravel", method= RequestMethod.POST)
    public ResponseBean addTravel(@RequestBody AddAndModifyTravelRequest request){
        return adminTravelService.addTravel(request);
    }

    @RequestMapping(value = "/admintravel/updateTravel", method= RequestMethod.POST)
    public ResponseBean updateTravel(@RequestBody AddAndModifyTravelRequest request){
        return adminTravelService.updateTravel(request);
    }

    @RequestMapping(value = "/admintravel/deleteTravel", method= RequestMethod.POST)
    public ResponseBean deleteTravel(@RequestBody DeleteTravelRequest request){
        return adminTravelService.deleteTravel(request);
    }

    @RequestMapping(value = "/admintravel/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {

                System.out.println("File Upload - File Name: " + file.getName());
                System.out.println("File Upload - File Original Filename" + file.getOriginalFilename());
                System.out.println("File Upload - File Size" + file.getSize());

                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();

                FileInputStream inputStream = new FileInputStream(file.getOriginalFilename());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String str = null;
                while((str = bufferedReader.readLine()) != null) {
                    System.out.println("[Upload Read Line]" + str);

//                    String[] strList = str.split("&");
//
//                    if(strList.length != 4){
//                        System.out.println("[Array Length]：" + strList.length);
//                        continue;
//                    }

//                    String startStationId = strList[0];
//                    String endStationId = strList[1];
//                    String stationListStr = strList[2];
//                    String distanceList = strList[3];

//                    CreateAndModifyRouteInfo info = new CreateAndModifyRouteInfo();
//                    info.setId("");
//                    info.setStartStation(startStationId);
//                    info.setEndStation(endStationId);
//                    info.setStationList(stationListStr);
//                    info.setDistanceList(distanceList);
//                    CreateAndModifyRouteResult result = restTemplate.postForObject(
//                            "http://ts-route-service:11178/route/createAndModify",
//                            info, CreateAndModifyRouteResult.class);
//                    if(result.isStatus() == true){
//                        System.out.println("[Upload Add Route]Add Route Success.");
//                    }else{
//                        System.out.println("[Upload Add Route]Add Route Fail.");
//                        System.out.println("[Upload Add Route]Reason == " + result.getMessage());
//                    }



                }

                //close
                inputStream.close();
                bufferedReader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "Upload fail," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Upload Fail," + e.getMessage();
            }
            return "Upload Success.";
        } else {
            return "Upload Fail. Because the file is empty.";
        }
    }
}
