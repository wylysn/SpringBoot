package com.purang.SpringBoot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class RedisSessionDemoController {
	@RequestMapping(value = "/first", method = RequestMethod.GET)  
    public Map<String, Object> firstResp (HttpServletRequest request){  
        Map<String, Object> map = new HashMap<>();  
        request.getSession().setAttribute("request Url", request.getRequestURL());  
        map.put("request Url", request.getRequestURL());  
        return map;  
    }  
  
    @RequestMapping(value = "/sessions", method = RequestMethod.GET)  
    public Object sessions (HttpServletRequest request){  
        Map<String, Object> map = new HashMap<>();  
        map.put("sessionId", request.getSession().getId());  
        map.put("message", request.getSession().getAttribute("request Url"));  
        return map;  
    }
}
