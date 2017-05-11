package com.myproject.msg;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessageSendingServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(MessageSendingServiceImpl.class);
	@Resource
	private SimpMessageSendingOperations messaging;
	
	/**
	 * 异步发送消息
	 * @param t
	 */
	@Async
	public <T> void sendMsgAsync(T t){
		this.sendMsg(t);
	}
	
	/**
	 * 同步发送信息
	 * @param t
	 */
	public <T> void sendMsgSync(T t){
		this.sendMsg(t);
	}
	
	private <T> void sendMsg(T t){
		try {
//			messaging.
			messaging.convertAndSend("/topic/spittlefeed", t);
		} catch (Exception e) {
			logger.error("MessageServiceImpl - sendMsg error",e);
		}
	}
	
}
