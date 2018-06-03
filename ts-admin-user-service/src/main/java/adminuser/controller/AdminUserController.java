package adminuser.controller;

import adminuser.domain.request.AddAccountRequest;
import adminuser.domain.request.DeleteAccountRequest;
import adminuser.domain.request.UpdateAccountRequest;
import adminuser.domain.response.DeleteAccountResult;
import adminuser.domain.response.FindAllAccountResult;
import adminuser.domain.response.ModifyAccountResult;
import adminuser.domain.response.RegisterResult;
import adminuser.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminuser/findAll/{id}", method = RequestMethod.GET)
    public FindAllAccountResult getAllUsers(@PathVariable String id){
        return adminUserService.getAllUsers(id);
    }

    @RequestMapping(value = "/adminuser/addUser", method= RequestMethod.POST)
    public RegisterResult addUser(@RequestBody AddAccountRequest request){
        return adminUserService.addUser(request);
    }

    @RequestMapping(value = "/adminuser/updateUser", method= RequestMethod.POST)
    public ModifyAccountResult updateOrder(@RequestBody UpdateAccountRequest request){
        return adminUserService.updateUser(request);
    }

    @RequestMapping(value = "/adminuser/deleteUser", method= RequestMethod.POST)
    public DeleteAccountResult deleteOrder(@RequestBody DeleteAccountRequest request){
        return adminUserService.deleteUser(request);
    }

    @RequestMapping(value = "/adminuser/upload", method = RequestMethod.POST)
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
