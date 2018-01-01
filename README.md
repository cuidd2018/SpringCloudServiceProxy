# SpringCloudServiceProxy
SpringCloudServiceProxy-一个实现动态代理服务调用层工具，基于spring cloud，</br>
相比原生spring cloud 不仅做到代码服务层无侵入，</br>
而且服务的参数和返回值 支持 几乎所有复杂对象（依赖与hessian 序列化），调用远程服务像调用本地服务一样方便</br>



demo 运行步骤</br>

step1: 运行 DemoDiscoveryApplication，DemoConsumersApplication，DemoServiceApplication</br>
step2: 浏览器访问 http://localhost:8080/invokeObject 即可 展示 传递复杂对象 的案例。</br>
