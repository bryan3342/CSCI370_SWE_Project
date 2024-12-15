package heartscope_final.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HeartScopeController {
    
    @RequestMapping(value="/chatBot.html", method = RequestMethod.GET)
    @ResponseBody // Important because you need template file to be returned.
    public String index(){
        return "index.html";
    }
    
    @RequestMapping(value="/home.html", method = RequestMethod.GET)
    public String home(){
        return "home.html";
    }
}
