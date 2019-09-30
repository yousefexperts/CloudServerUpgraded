package com.experts.core.biller.statemachine.api.activemq.standers.config;

/**
 * @author Ulises Bocchio
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public ModelAndView home(@SAMLUser SamlUserDetails user) {
        ModelAndView homeView = new ModelAndView("home");
        homeView.addObject("userId", user.getUsername());
        homeView.addObject("samlAttributes", user.getAttributes());
        return homeView;
    }

}
