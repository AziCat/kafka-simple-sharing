package yan.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类
 *
 * @author yanjunhao
 * @date 2019年7月16日
 */
@SpringBootApplication(scanBasePackages = {"yan.study"})
public class ConsumerApplicationMain {


    /**
     * 应用启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplicationMain.class, args);
        System.out.println("start success!");
    }

}
