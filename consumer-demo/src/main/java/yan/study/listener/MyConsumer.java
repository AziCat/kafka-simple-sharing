package yan.study.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费者
 *
 * @author yanjunhao
 * @date 2019年7月17日
 */
@Component
public class MyConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MyConsumer.class);

    @KafkaListener(id = "myTopicGroup",
            topics = "myTopic")
    public void listener(List<Message<String>> list, Acknowledgment ack) {
        list.forEach(message -> {
            /* kafka-0.11头部包含：
             * kafka_offset-Long
             * kafka_consumer-KafkaConsumer
             * kafka_timestampType-String
             * kafka_receivedMessageKey-
             * kafka_receivedPartitionId-Integer
             * kafka_receivedTopic-String
             * kafka_receivedTimestamp-Long
             * kafka_acknowledgment-KafkaMessageListenerContainer$ListenerConsumer$ConsumerBatchAcknowledgment
             */
            String topic = (String) message.getHeaders().get("kafka_receivedTopic");
            Long offset = (Long) message.getHeaders().get("kafka_offset");
            String body = message.getPayload();
            logger.info("get message from topic[{}],offset[{}],message body[{}]", topic, offset, body);
            //提交offset，不提交的话下次启动消费者会重复消费
            ack.acknowledge();
        });
    }
}
