package com.example.ticketPlatform.configuration;

import com.example.ticketPlatform.Listener.RedisNotificationSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    // 1. Define the Topic Name
    public static final String NOTIFICATION_TOPIC = "global-notifications";

    // 2. Configure the Container
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Subscribe to the global topic
        container.addMessageListener(listenerAdapter, new ChannelTopic(NOTIFICATION_TOPIC));
        return container;
    }

    // 3. Link the Subscriber (defined in Step 2) to the Listener
    @Bean
    MessageListenerAdapter listenerAdapter(RedisNotificationSubscriber subscriber) {
        // This tells Spring to call the "handleMessage" method in your subscriber class
        return new MessageListenerAdapter(subscriber, "handleMessage");
    }
}