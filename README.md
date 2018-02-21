# SpringCloudServiceProxy
SpringCloudServiceProxy-一个实现动态代理服务调用层工具，基于spring cloud，</br>
SpringCloud全家桶提供了诸多功能，如果你跃跃欲试，又发现服务层调用困难，Feign又是个大坑侵入严重，@ResponseBody注解无法解决多个复杂JSON对象的传递。</br>

===此框架优势===</br>
单体迁移到分布式系统 简单 免重构</br>
类似Dubbo使用动态代理提供每个Service对象的伪实例，内方法实现则使用Ribbon负载均衡的RestTemplete</br>
相比原生spring cloud 不仅做到代码服务层无侵入，</br>
而且服务的参数和返回值 支持 几乎所有复杂对象包括文件（依赖 FST 序列化,http协议流传输（Stream流）），调用远程服务像调用本地服务一样方便</br>

#如何引入项目？
Maven项目，首先加入pom

```xml
	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```xml
<dependencies>
...
<dependency>
	 <groupId>com.github.zhuxiujia</groupId>
	 <artifactId>SpringCloudServiceProxy</artifactId>
	 <version>v1.2</version>
</dependency>
</dependencies>
```

demo 运行步骤</br>

step1: 运行 DemoDiscoveryApplication，DemoConsumersApplication，DemoServiceApplication</br>
step2: 浏览器访问 http://localhost:8080/invokeObject 即可 展示 传递复杂对象 的案例。</br>
