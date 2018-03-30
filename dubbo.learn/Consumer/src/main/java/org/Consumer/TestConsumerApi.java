package org.Consumer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.test.UserServiceBo;

/**
 * Hello world!
 *
 */
public class TestConsumerApi {
	public static void main(String[] args) throws InterruptedException {
		// 等价于  <dubbo:application name="dubboConsumer" />  
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubboConsumer");
		
		// 等价于     <dubbo:registry  protocol="zookeeper" address="zookeeper://127.0.0.1:2181" />  
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");

		//等价于 	<dubbo:monitor protocol="registry" />
		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setProtocol("registry");

		//等价于<dubbo:reference id="userService" interface="com.test.UserServiceBo"
		//group="dubbo" version="1.0.0" timeout="3000" />
		ReferenceConfig<UserServiceBo> reference = new ReferenceConfig<UserServiceBo>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setInterface(UserServiceBo.class);
		reference.setVersion("1.0.0");
		reference.setGroup("dubbo");
		reference.setTimeout(3000);
		reference.setInjvm(false);
		reference.setMonitor(monitorConfig);
		
        
		UserServiceBo userService = reference.get(); 
		System.out.println(userService.sayHello("哈哈哈"));
		Thread.currentThread().join();
		
	}
}
