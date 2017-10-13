package com.purang.SpringBoot.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/*
 * 解决ErrorConfiguration里面设置AuthorizationException被HttpStatus.INTERNAL_SERVER_ERROR和java.lang.Throwable.class覆盖的问题
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = org.apache.shiro.authz.AuthorizationException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("/error/403");
        return mav;
    }
}
