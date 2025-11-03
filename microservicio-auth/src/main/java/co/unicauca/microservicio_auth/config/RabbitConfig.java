package co.unicauca.microservicio_auth.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Exchange para eventos de usuario
    public static final String USER_EXCHANGE = "userExchange";

    // Colas destino (en otros microservicios)
    public static final String PROCESS_USER_QUEUE = "processUserQueue";
    public static final String DOCUMENT_USER_QUEUE = "documentUserQueue";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue processQueue() {
        return new Queue(PROCESS_USER_QUEUE, true);
    }

    @Bean
    public Queue documentQueue() {
        return new Queue(DOCUMENT_USER_QUEUE, true);
    }

    @Bean
    public Binding bindProcessQueue() {
        return BindingBuilder.bind(processQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindDocumentQueue() {
        return BindingBuilder.bind(documentQueue()).to(fanoutExchange());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}