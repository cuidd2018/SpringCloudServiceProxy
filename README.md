# SpringCloudServiceProxy
SpringCloudServiceProxy-一个实现动态代理服务调用层工具，基于spring cloud，</br>
## 市面各框架对比：
-SpringCloud，SpringCloud全家桶提供了诸多功能，发现Feign只支持Json格式的传输，Feign侵入严重，@ResponseBody注解无法解决多个复杂JSON对象的传递。</br>
-Dubbo框架，采用dubbo协议，dubbo协议采用单一长连接和NIO异步通讯，适合于小数据量大并发的服务调用，不适合传送大数据量的服务，比如传文件，传视频等，除非请求量很低。 </br>

===此框架优势===</br>
### 基于更成熟更全面的Spring Cloud全家桶 微服务解决方案
### 提供相比Feign性能更好的服务调用
Apache ab压力测试工具实测：2000并发，50000个请求，调用复杂JavaBean完美（不代表最终性能，此处只是案例，具体看客户电脑配置性能）命令 ab.exe -n 50000 -c 2000 http://localhost:18080/invokeObject</br>
### 单体迁移到分布式系统 免重构 代码0无侵入
使用动态代理提供每个Service对象的伪实例，内方法实现则使用Ribbon负载均衡的RestTemplete</br>
### 相比Dubbo完美支持服务-服务-消费者-之间的 大文件传输
而且服务的参数和返回值 支持 几乎所有复杂对象包括文件（依赖 FST 快速高效序列化,http协议流传输（Stream流）），调用远程服务像调用本地服务一样方便</br>
# 如何引入项目？
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
<dependency>
   <groupId>com.github.zhuxiujia</groupId>
   <artifactId>SpringCloudServiceProxy</artifactId>
   <version>v1.4</version>
</dependency>
</dependencies>
```

# demo 运行步骤

step1: 运行 DemoDiscoveryApplication，DemoConsumersApplication，DemoServiceApplication</br>
step2: 浏览器访问 http://localhost:8080/invokeObject 即可 展示 传递复杂对象 的案例。</br>
