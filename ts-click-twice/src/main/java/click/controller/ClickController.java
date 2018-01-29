package click.controller;

import click.async.AsyncTask;
import click.domain.OrderTicketsInfo;
import click.domain.OrderTicketsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.Future;

@RestController
public class ClickController {

    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to auto ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/click/auto", method = RequestMethod.POST)
    public boolean auto() throws Exception{


        //登录
        //搜寻车票
        //选择联系人
        //点三次确认车票，并等待结果
        //去高级查询里点击两次车票

        return false;
    }

}
