package cn.insight.sys.admin.controller;

import cn.insight.configuration.JboneConfiguration;
import cn.insight.sys.common.UserResponseDO;
import cn.insight.sys.core.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;

public class CommonAppsController {

    public void setCurrentUser(UserService userService, ModelMap modelMap, JboneConfiguration jboneConfiguration){
        UserResponseDO userResponseDO = (UserResponseDO) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        modelMap.put("user", userResponseDO);
    }
}
