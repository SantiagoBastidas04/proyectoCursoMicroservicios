package co.edu.unicauca.process_management.adapter.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String PROCESS_USER_QUEUE = "processUserQueue";
    public static final String DOCUMENT_QUEUE = "DocumentQueue";

    @Bean
    public Queue documentQueue() {return new Queue(DOCUMENT_QUEUE, true);}

    @Bean
    public Queue processUserQueue() {return new Queue(PROCESS_USER_QUEUE, true);}

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonConverter());
        return factory;
    }
}
