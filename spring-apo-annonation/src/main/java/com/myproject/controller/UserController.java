package com.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.aop.LogAnnotation;

@RequestMapping("user")
@Controller
public class UserController {
	
	@LogAnnotation("获取用户列表")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String getUserList(){
		return "list";
	}
	
	@LogAnnotation("根据id获取单个用户")
	@RequestMapping(method=RequestMethod.GET,value="{id}")
	@ResponseBody
	public String getUserById(@PathVariable int id){
		if(id == 0){
			throw new NullPointerException();
		}
		return "one user";
	}

}
