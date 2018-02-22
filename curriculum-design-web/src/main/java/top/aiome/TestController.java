package top.aiome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 马红岩 on 2018/1/15.
 */
@Controller
@RequestMapping("/test")
public class TestController {
//    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @RequestMapping(value = "getName")
    @ResponseBody
    public Map getName(){
        Map map = new HashMap<String,String>();
        map.put("name",testService.getName());
        return map;
    }

    @RequestMapping("a")
    public ModelAndView get(){
        return new ModelAndView("test");
    }
}
