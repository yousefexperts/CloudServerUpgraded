package cn.insight.sys.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.insight.sys.core.service.SystemService;

@Controller
@RequestMapping("service")
public class JboneServiceController {
    @Autowired
    private SystemService systemService;

    @RequestMapping("getTheme")
    @ResponseBody
    public String getServiceTheme(@RequestParam("service")String service){
        return systemService.findServiceTheme(service);
    }
}
