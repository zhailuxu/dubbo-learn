package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProvider {   

	public static void main(String[] arg) throws InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");

	    //挂起当前线程，如果没有改行代码，服务提供者进程会消亡，服务消费者就发现不了提供者了
		Thread.currentThread().join();
	}
}
