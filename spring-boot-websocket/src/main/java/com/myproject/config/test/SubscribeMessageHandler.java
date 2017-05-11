package com.myproject.config.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
@Controller
public class SubscribeMessageHandler {
	@Resource
	private SimpMessageSendingOperations messaging;
	
	/**
	 * <i>@SubscribeMapping注解必须处在@EnableWebSocketMessageBroker标注类的子包内，
	 * 否则将不能被websocket框架扫描到
	 * </i>
	 * <i>@SubscribeMapping注解必须在@Controller组件下才能生效</i>
	 * @return
	 */
	@SubscribeMapping("/subscribMessage")
	public String subscribeMessage(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "hello websocket message from @SubscribeMapping now=" + simpleDateFormat.format(new Date());
	}
	
	@Scheduled(fixedRate = 1000)
	public void Test(){
		this.subscribeMessage();
	}
}
