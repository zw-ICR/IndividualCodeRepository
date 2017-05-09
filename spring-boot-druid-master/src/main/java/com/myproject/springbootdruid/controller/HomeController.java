package com.myproject.springbootdruid.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myproject.springbootdruid.mapper.UserInfoMapper;
import com.myproject.springbootdruid.mapper.UserInfoMapperAnnonation;
import com.myproject.springbootdruid.model.UserInfo;

@Controller
public class HomeController {

    private Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private UserInfoMapperAnnonation userInfoMapperAnno;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model Model){
        Model.addAttribute("name","liangchong998");
        return "index";
    }

    @RequestMapping(value = "userlist",method = RequestMethod.GET)
    @ResponseBody
    public List<UserInfo> userList(@RequestParam(value = "page",defaultValue = "1")int page,
                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageHelper.startPage(page,pageSize);
        Page<UserInfo> pageInfos = userInfoMapper.selectAll();
        return pageInfos;
    }
   
}
