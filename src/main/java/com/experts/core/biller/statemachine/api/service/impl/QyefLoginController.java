package com.experts.core.biller.statemachine.api.service.impl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Controller
@Component("qyefLoginController")
public class QyefLoginController {


    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;


    @GetMapping(value = "/login", path = "/login", name = "/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @GetMapping(value = "/core", path = "/core", name = "/core")
    public ModelAndView core() {
        return new ModelAndView("core");
    }




}
