package com.myproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 场景：客户端主动收发信息
 * spring boot启动时候启动
 * 
 * @author kevinChan
 * <i>若使用注解@MessageMapping和@SubscribeMapping等注解方式来定义websock主题，
 * 	  定义类必须放在@EnableWebSocketMessageBroker注解所在类的子包里面，否则不能被websocket组件扫描到
 * </i>
 *
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	/**
	 * Stomp是一个简单的消息文本协议,它的设计核心理念就是简单与可用性
	 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	System.out.println("configureMessageBroker>>>"+"服务器已经启动");
    	//代理消息地址
    	//这里设置的simple broker是指可以订阅的地址，也就是服务器可以发送的地址
        config.enableSimpleBroker("/topic","/public");//设置服务端广播消息的基础路径（客户端接收服务端广播消息的地址的前缀信息）
        config.setApplicationDestinationPrefixes("/app");//设置客户端订阅消息的基础路径（客户端给服务端发消息的地址的前缀）
    }

    /**
     * 设置websocket握手地址
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	System.out.println("registerStompEndpoints>>>"+"注册消息连接");
    	//第一个方法，是registerStompEndpoints，大意就是注册消息连接点，所以我们进行了连接点的注册：
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();
  
    }
 
}
