package com.example.demo_consumers.test;


import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class InvkeCountTest {

    public static int MaxCount = 1000;
    public static int MaxThread = 500;
    private static final Class<? extends byte[]> byteArrayClassType = byte[].class;

    public static void main(String[] args) throws IOException {
        //TestJob.main(args);

       // TestJobMono.main(args);
//        Mono<byte[]> mono=Mono.create(new Consumer<MonoSink<byte[]>>() {
//            @Override
//            public void accept(MonoSink<byte[]> monoSink) {
//                ServiceDTO serviceDTO=new ServiceDTO();
//                serviceDTO.setMethod("sayHello");
//                serviceDTO.setService("com.example.demo_service_interface.service.DemoService");
//                serviceDTO.setParams(null);
//                serviceDTO.setParamsTypes(null);
//                try {
//                    byte[] bytes = SerializeUtil.serialize(serviceDTO);
//                    monoSink.success(bytes);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        String bytes ;
        ServiceDTO serviceDTO=new ServiceDTO();
        serviceDTO.setMethod("sayHello");
        serviceDTO.setService("com.example.demo_service_interface.service.DemoService");
        serviceDTO.setParams(null);
        serviceDTO.setParamsTypes(null);
        try {
             bytes = SerializeUtil.serialize(serviceDTO);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Resource resource= WebClient.create("http://localhost:8082/everything").put().body(mono,byte[].class).accept(MediaType.ALL)
//              .exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Resource.class)).block(Duration.ofSeconds(30));


     Mono<byte[]> bytemono=    WebClient.create("http://localhost:8082/everything").post().body(BodyInserters.fromObject(serviceDTO)).retrieve().bodyToMono(byte[].class);


//        Object result = SerializeUtil.deserialize(bytemono.block());
//       System.out.println(JSON.toJSONString(result));


    }

}
