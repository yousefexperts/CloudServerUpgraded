package com.experts.core.biller.statemachine.api.cas.web.flow;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;
import com.experts.core.biller.statemachine.api.cas.authentication.UsernamePasswordCaptchaCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserAuthenticationViaFormAction extends AuthenticationViaFormAction {

    public final String validatorCaptcha(final RequestContext context, final Credential credential,
                                         final MessageContext messageContext) {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();
        String captcha = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        UsernamePasswordCaptchaCredential upc = (UsernamePasswordCaptchaCredential) credential;
        String submitAuthcodeCaptcha = upc.getCaptcha();
        if (!StringUtils.hasText(submitAuthcodeCaptcha) || !StringUtils.hasText(submitAuthcodeCaptcha)) {
            messageContext.addMessage(new MessageBuilder().code("required.captcha").build());
            return ERROR;
        }
        if (submitAuthcodeCaptcha.equals(captcha)) {
            return SUCCESS;
        }
        messageContext.addMessage(new MessageBuilder().code("error.authentication.captcha.bad").build());
        return ERROR;
    }
}
