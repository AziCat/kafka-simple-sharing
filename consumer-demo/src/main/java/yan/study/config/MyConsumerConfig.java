package yan.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;

/**
 * 配置类
 *
 * @author yanjunhao
 * @date 2019年7月17日
 */
@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProperties.class)
public class MyConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyConsumerConfig.class);
    @Value("${spring.kafka.consumer.concurrency:4}")
    private Integer currency;
    private final KafkaProperties kafkaProperties;

    public MyConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        //关闭消费者自动启用,应用启用后再根据配置启用
        //factory.setAutoStartup(false);
        //设置过滤器
        //factory.setRecordFilterStrategy(myKafkaRecordFilter);
        //启用批量监听
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter());
        //消费者个数，消费者个数不能超过分区数
        factory.setConcurrency(currency);
        //offset提交方式
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        logger.info("create bean [ConcurrentKafkaListenerContainerFactory] success !");
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                this.kafkaProperties.buildConsumerProperties());
    }
}
