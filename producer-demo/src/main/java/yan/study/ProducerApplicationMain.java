package yan.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 应用启动类
 *
 * @author yanjunhao
 * @date 2019年7月16日
 */
@SpringBootApplication(scanBasePackages = {"yan.study"})
public class ProducerApplicationMain implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 应用启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplicationMain.class, args);
        System.out.println("start success!");
    }

    @Override
    public void run(String... args) throws Exception {
        //KafkaTemplate javadoc see:https://docs.spring.io/spring-kafka/api/org/springframework/kafka/core/KafkaTemplate.html
        //不指定key会平均发送到不同的Partition
        kafkaTemplate.send("myTopic","Hello world");
        kafkaTemplate.send("myTopic","Hello world2");
        kafkaTemplate.send("myTopic","Hello world3");
        //指定Key会根据key发送到同一Partition中
        kafkaTemplate.send("myTopic","key","aaaaa");
        kafkaTemplate.send("myTopic","key","bbbbb");
    }
}
