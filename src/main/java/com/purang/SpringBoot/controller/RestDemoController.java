package com.purang.SpringBoot.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purang.SpringBoot.domain.ResponseData;
import com.purang.SpringBoot.domain.UserEntity;

@RestController
@RequestMapping("/json")
public class RestDemoController {

	@RequestMapping("/first")
	UserEntity home() {
		UserEntity user = new UserEntity();
		user.setId(new Long(123));
		user.setUserName("testname");
		return user;
	}
	
	@RequestMapping("/second")
	ResponseData homeBack() {
		ArrayList<UserEntity> list = new ArrayList<>();
		
		UserEntity user1 = new UserEntity();
		user1.setId(new Long(1));
		user1.setUserName("testname1");
		UserEntity user2 = new UserEntity();
		user2.setId(new Long(2));
		user2.setUserName("testname2");
		list.add(user1);
		list.add(user2);
		
		ResponseData json = new ResponseData();
		json.setCode("200");
		json.setSuccess("true");
		json.setData(list);
		return json;
	}
	
	@RequestMapping("/testAround")  
	UserEntity testAroundService(String key){  
		UserEntity user = new UserEntity();
		user.setId(new Long(123));
		user.setUserName("testname");
		return user; 
    }
	
}
