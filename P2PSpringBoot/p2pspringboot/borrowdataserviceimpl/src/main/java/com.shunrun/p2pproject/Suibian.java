package com.shunrun.p2pproject;



import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by jackiechan on 18-7-18/下午4:12
 */
//因为springboot是有项目启动类的，所以为了打成war包在tomcat也能访问，就需要定义这个类，类名随便取即可
public class Suibian extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }
}
