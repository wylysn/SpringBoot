package com.purang.SpringBoot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.purang.SpringBoot.domain.ResponseData;

@Controller
@RequestMapping("/error")
public class GlobleErrorController implements ErrorController {
	private static final String PATH = "/error";
	
	@RequestMapping(produces="text/html", value="400")
    public String badRequestOfHtml() {
        return "error/400";
    }
	
	@RequestMapping(value="400")
	@ResponseBody
    public ResponseData badRequest() {
		ResponseData data = new ResponseData();
		data.setCode("400");
		data.setSuccess("false");
		data.setMsg("400错误!");
		data.setData("");
        return data;
    }

	//针对浏览器html请求
	@RequestMapping(produces="text/html", value="404")
    public String notFoundOfHtml() {
        return "error/404";
    }

	//针对rest api,返回json数据
	@RequestMapping(value="404")
	@ResponseBody
    public ResponseData notFound(HttpServletRequest request) {
		ResponseData data = new ResponseData();
		data.setCode("404");
		data.setSuccess("false");
		data.setMsg("404错误!");
		data.setData("");
        return data;
    }
	
	@RequestMapping(produces="text/html", value="500")
    public String serverError() {
        return "error/500";
    }
	
	@Override
    public String getErrorPath() {
        return PATH;
    }
}
