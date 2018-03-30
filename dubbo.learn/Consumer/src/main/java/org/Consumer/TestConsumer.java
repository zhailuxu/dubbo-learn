package org.Consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.UserServiceBo;

/**
 * Hello world!
 *
 */
public class TestConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:consumer.xml" });

		final UserServiceBo demoService = (UserServiceBo) context.getBean("userService");

		System.out.println(demoService.sayHello("哈哈哈"));
	}
}
