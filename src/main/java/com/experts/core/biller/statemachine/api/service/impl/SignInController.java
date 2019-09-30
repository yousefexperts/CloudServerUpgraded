package com.experts.core.biller.statemachine.api.service.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component("signInController")
public class SignInController {
    @GetMapping(name  = "/signin" , value = "/signin" , path = "/signin")
    public ModelAndView signin(){
        return new ModelAndView("signin");
    }


    @GetMapping(name  = "/phone" , value = "/phone" , path = "/phone")
    public ModelAndView phone(){
        return new ModelAndView("phone");
    }

}
