package com.tf.security;
import com.tf.utils.ResultR;
import com.tf.web.config.ErrCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 拦截禁止
 */
public class MyHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint {
    public MyHttp403ForbiddenEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
//        ResultR resultR = new ResultR(ErrCode.NO_LOGIN);
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getOutputStream().println(resultR.getResult());
    }
}