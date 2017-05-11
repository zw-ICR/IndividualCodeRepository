package com.myproject.config.test;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.myproject.bean.Message;

/**
 * 本例用来处理客户端通过websocket发送的消息
 * @author zw
 *
 */
@Controller
public class MessageReceiveHandler {
	@Resource
    private SimpMessagingTemplate simpMessagingTemplate; 
	
	/**
	 * <i>@SubscribeMapping注解必须处在@EnableWebSocketMessageBroker标注类的子包内，
	 * 否则将不能被websocket框架扫描到
	 * </i>
	 * <i>@SubscribeMapping注解必须在@Controller组件下才能生效</i>
	 * @return
	 */
	@MessageMapping("/receive")
	public void recevie(Message msg){
		System.out.println("msg=" + msg.getText());
		msg.setText("receive msg from client");
		simpMessagingTemplate.convertAndSend("/topic/receiveMsg",msg);
	}

}
