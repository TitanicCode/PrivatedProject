package com.shenrun.p2pproject.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2018/7/26.
 */
public class MyContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /**
         * 程序加载的时候
         */
        //查询出所有的用户类型
        System.out.println("程序启动了");
        //问题1:要查询数据库必须有mapper的代理对象,我们的mapper代理对象是spring创建的,但是tomcat启动的时候spring还没有加载呢，这个地方没有spring

            //解决方式一：可以使用原生的jdbc,因为此方法只会随着程序的启动执行一次,所以不会有高并发等情况

            //解决方式二：不使用listener,我使用spring创建对象的机制


        //链接到redis中,将数据放到redis中

        //放到reids中的什么类型的数据上面(hash中存放,因为它可以有大key和小key)
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /**
         * 程序销毁也就是从tomcat中移除或者关闭tomcat
         */
    }
}
