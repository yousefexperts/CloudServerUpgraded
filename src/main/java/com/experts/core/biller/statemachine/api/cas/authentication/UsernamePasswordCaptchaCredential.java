package com.experts.core.biller.statemachine.api.cas.authentication;

import org.jasig.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsernamePasswordCaptchaCredential extends RememberMeUsernamePasswordCredential {

    private static final long serialVersionUID = -4200526882497123391L;

    @NotNull
    @Size(min = 1, message = "required.captcha")
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
