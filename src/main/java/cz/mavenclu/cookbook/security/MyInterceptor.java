package cz.mavenclu.cookbook.security;


import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) !=  null){
            String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        }else {
            response = null;    //todo dokoncit
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
