package cn.cas.spring.insight.bpm.admin.controller;

import cn.insight.configuration.JboneConfiguration;
import cn.insight.sys.api.UserApi;
import cn.insight.sys.common.UserResponseDO;

import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;

public class CommonController {


    public void setCurrentUser(ModelMap modelMap, UserApi userApi, JboneConfiguration jboneConfiguration) {
        UserResponseDO currentUser = (UserResponseDO) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        modelMap.put("user", currentUser);
    }
}
