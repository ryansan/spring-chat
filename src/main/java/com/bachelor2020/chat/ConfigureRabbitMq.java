package com.bachelor2020.chat;

import com.bachelor2020.chat.consumer.ReceiveMessageService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMq {

    public static final String QUEUE_NAME = "testqueue";
    public static final String EXCHANGE_NAME = "testexchange";

    @Bean
    Queue createQueue(){
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue q, TopicExchange exchange){
        return BindingBuilder.bind(q).to(exchange).with("test.#");
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ReceiveMessageService service){
        return new MessageListenerAdapter(service, "handleMessage");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

