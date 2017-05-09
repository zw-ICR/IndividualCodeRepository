package com.myproject.springbootdruid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myproject.springbootdruid.mapper.UserInfoMapperAnnonation;
import com.myproject.springbootdruid.model.UserInfo;

@Controller
@RequestMapping("userInfo")
public class UserInfoController {
	@Autowired
	private UserInfoMapperAnnonation userInfoMapperAnnonation;
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void addUser(@RequestBody UserInfo userInfo){
		userInfoMapperAnnonation.insertT(userInfo);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void updateUser(@RequestBody UserInfo userInfo){
		userInfoMapperAnnonation.updateT(userInfo);
	}
	
	
	@RequestMapping(value = "{userInfoId}", method=RequestMethod.GET)
	@ResponseBody
	public UserInfo getUserInfo(@PathVariable Long userInfoId){
		return userInfoMapperAnnonation.getUser(userInfoId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserInfo> userListAnnonation(@RequestParam(value = "page",defaultValue = "1")int page,
                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageHelper.startPage(page,pageSize);
        Page<UserInfo> pageInfos = userInfoMapperAnnonation.selectAll();
        return pageInfos;
    }
	
	

}
