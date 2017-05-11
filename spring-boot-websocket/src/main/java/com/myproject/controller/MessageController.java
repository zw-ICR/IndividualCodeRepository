package com.myproject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.myproject.msg.MessageSendingServiceImpl;


@Controller
@RequestMapping("message")
public class MessageController {
	@Autowired
	private MessageSendingServiceImpl messageSendingServiceImpl;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public void Test(){
		messageSendingServiceImpl.sendMsgAsync("hello websocket");
	}
	
	@Scheduled(fixedRate = 1000)
	public void TestAuthSendMsg(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageSendingServiceImpl.sendMsgAsync("hello websocket Now:" + simpleDateFormat.format(new Date()));
	}

}
