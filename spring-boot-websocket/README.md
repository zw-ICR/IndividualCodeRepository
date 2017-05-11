# spring-boot-websocket

<h2>spring-boot + websocket</h2>
<h2>界面端使用sockjs + stomp</h2>
当使用注解来标注websocket方法时。2个重要的点需要注意
<i>@SubscribeMapping注解必须处在@EnableWebSocketMessageBroker标注类的子包内，否则将不能被websocket框架扫描到</i>
<i>@SubscribeMapping注解必须在@Controller组件下才能生效</i>

