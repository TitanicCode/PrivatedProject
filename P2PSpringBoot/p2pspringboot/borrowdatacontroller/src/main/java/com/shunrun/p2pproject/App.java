package com.shunrun.p2pproject;

import com.common.fastdfs.util.FastdfsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by jackiechan on 18-7-18/下午4:08
 */
//该类为springboot的启动类，自带tomcat，启动springboot项目的时候直接启动该类就行
//@SpringBootApplication作为项目启动类加扫描类不能直接出现在java文件夹下，即至少需要在Java文件夹下建一层包放这个类
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    //FastdfsClient是我们导入的自己创建的第三方jar包，它没有spring的注解，所以没法在启动时由spring管理并创建对象，因此也就没法自动注入
    //为了能够在其他类里能自动注入FastdfsClient对象，可以在xml文件中配置，而springboot项目可以直接在@SpringBootApplication启动类或者@Configuration配置类中创建对象，这样就可以在其他类中自动注入了
    @Bean
    public FastdfsClient getFastdfsClient() {
        try {
            FastdfsClient fastdfsClient=new FastdfsClient("classpath:tracker.conf");
            return  fastdfsClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
