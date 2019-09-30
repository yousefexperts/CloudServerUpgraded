<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<jsp:directive.include file="includes/top.jsp" />
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车易拍 SSO</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/blue.css">
    <link rel="icon" type="image/png" href="/images/favicon.ico" sizes="32x32" />
    <!--[if lt IE 9]>
    <script src="/js/html5shiv.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <b>车易拍</b>SSO
    </div>
    <div class="login-box-body">
        <p class="login-box-msg"><form:errors path="*" element="h2" htmlEscape="false"/></p>
        <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
            <div class="form-group has-feedback">
                <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey"/>
                <spring:message code="screen.welcome.label.netid" var="userNameHolder"/>
                <form:input id="username" size="25" tabindex="1" path="username"
                            autocomplete="off" htmlEscape="true" class="form-control"
                            accesskey="${userNameAccessKey}"
                            placeholder="${userNameHolder}"/>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey"/>
                <spring:message code="screen.welcome.label.password" var="passwordHolder"/>
                <form:password id="password" size="25" tabindex="2" path="password"
                               autocomplete="off" htmlEscape="true" class="form-control"
                               accesskey="${passwordAccessKey}"
                               placeholder="${passwordHolder}"/>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <spring:message code="screen.welcome.label.captcha.accesskey" var="captchaAccessKey"/>
                <spring:message code="screen.welcome.label.captcha" var="captchaHolder"/>
                <div class="col-sm-5" style="padding-left:0px;">
                    <form:input id="captcha" size="6" tabindex="3" path="captcha"
                                autocomplete="off" htmlEscape="true" class="form-control"
                                accesskey="${captchaAccessKey}"
                                placeholder="${captchaHolder}"/></div>
                <div class="col-sm-3">
                    <img alt="${captchaHolder }" style="cursor: pointer;height: 34px;" id="img_captcha"
                         src="captcha.jpg"/>
                </div>
            </div>
            <div class="row">
                <br/>
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input tabindex="4" type="checkbox" id="rememberMe" name="rememberMe"/>
                            <spring:message code="screen.welcome.label.rememberMe"/>
                        </label>
                    </div>
                </div>
                <div class="col-xs-4">
                    <input type="hidden" name="lt" value="${loginTicket}"/>
                    <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                    <input type="hidden" name="_eventId" value="submit"/>
                    <input name="submit" accesskey="l" class="btn btn-primary btn-block btn-flat"
                           value="<spring:message code="screen.welcome.button.login" />" tabindex="5" type="submit"/>
                </div>
            </div>
        </form:form>
        <div class="social-auth-links text-center"></div>
        <%--<a href="#">I forgot my password</a><br>--%>
        <%--<a href="#" class="text-center">Register a new membership</a>--%>
    </div>
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/icheck.min.js"></script>
<script>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
        $('#img_captcha').click(function () {
            $(this).prop('src', '/captcha.jpg?' + Math.random());
        })
    });
</script>
</body>
</html>