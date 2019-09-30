package cn.insight.sys.admin.controller;

import cn.insight.configuration.JboneConfiguration;
import cn.insight.sys.core.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexAppsController extends CommonAppsController {
    @Autowired
    JboneConfiguration jboneConfiguration;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(ModelMap modelMap){
        setCurrentUser(userService,modelMap,jboneConfiguration);
        return "index";
    }

    @RequestMapping("dashboard")
    public String dashboard(ModelMap modelMap){
        setCurrentUser(userService,modelMap,jboneConfiguration);
        return "pages/dashboard";
    }
}
