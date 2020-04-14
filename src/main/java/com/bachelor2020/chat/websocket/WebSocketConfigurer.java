package com.bachelor2020.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurer implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.enableSimpleBroker("/chat-room");
        //registry.setApplicationDestinationPrefixes("/chat-app");

        //RabbitMQ configurations
        registry
                .setApplicationDestinationPrefixes("/chat-app")
                .enableStompBrokerRelay("/topic")
                .setRelayHost("134.209.235.128")
                .setRelayPort(61613)
                .setClientLogin("admin")
                .setClientPasscode("6855eb384c10ca01fa654f");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sock").setAllowedOrigins("*").withSockJS();
    }
}
