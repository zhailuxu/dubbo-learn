package org.Consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.test.UserServiceBo;

/**
 * Hello world!
 *
 */
public class TestConsumerAsync {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubboConsumer");

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");

		// 引用远程服务
		ReferenceConfig<UserServiceBo> reference = new ReferenceConfig<UserServiceBo>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(UserServiceBo.class);
		reference.setVersion("1.0.0");
		reference.setGroup("dubbo");
		reference.setTimeout(3000);

		//（1）设置为异步调用
		reference.setAsync(true);

		// 和本地bean一样使用xxxService
		UserServiceBo userService = reference.get();

		long startTime = System.currentTimeMillis() / 1000;

		// （2）因为异步调用，此处返回null
		System.out.println(userService.sayHello("哈哈哈"));
		// 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
		Future<String> userServiceFutureOne = RpcContext.getContext().getFuture();

		// （3）因为异步调用，此处返回null
		System.out.println(userService.sayHello2("哈哈哈2"));
		// 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
		Future<String> userServiceFutureTwo = RpcContext.getContext().getFuture();


		// （4）阻塞到get方法，等待结果返回
		System.out.println(userServiceFutureOne.get());
		System.out.println(userServiceFutureTwo.get());
		long endTime = System.currentTimeMillis() / 1000;

		System.out.println("costs:" + (endTime - startTime));

	}
}
