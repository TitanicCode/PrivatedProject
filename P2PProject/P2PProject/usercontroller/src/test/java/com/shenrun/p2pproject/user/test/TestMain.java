package com.shenrun.p2pproject.user.test;



import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jackiechan on 18-7-18/上午10:20
 */
public class TestMain {
    @Test
    public void test1() throws  Exception {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("1234234234");
        Thread.sleep(50000);

    }
}
