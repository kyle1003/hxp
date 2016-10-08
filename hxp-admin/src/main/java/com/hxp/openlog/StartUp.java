package com.hxp.openlog;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/31.
 */
public class StartUp {

    public static void main(String [] args){
        System.out.println( "服务正在启动" );
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[] {"config/applicationContext.xml"});
            context.start();
            System.out.println( "服务启动完成" );
            System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
        } catch (IOException e) {
            System.out.println( "服务出问题了啊 ,出来吧 神龙" );
            e.printStackTrace();
        }

    }
}
