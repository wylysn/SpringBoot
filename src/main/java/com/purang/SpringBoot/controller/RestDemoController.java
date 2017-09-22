package com.purang.SpringBoot.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purang.SpringBoot.model.ResponseData;
import com.purang.SpringBoot.model.User;

@RestController
@RequestMapping("/json")
public class RestDemoController {

	@RequestMapping("/first")
	User home() {
		User user = new User();
		user.setId(1234);
		user.setName("testname");
		return user;
	}
	
	@RequestMapping("/second")
	ResponseData homeBack() {
		ArrayList<User> list = new ArrayList<>();
		
		User user1 = new User();
		user1.setId(1);
		user1.setName("testname1");
		User user2 = new User();
		user2.setId(2);
		user2.setName("testname2");
		list.add(user1);
		list.add(user2);
		
		ResponseData json = new ResponseData();
		json.setCode("200");
		json.setSuccess("true");
		json.setData(list);
		return json;
	}
	
}
