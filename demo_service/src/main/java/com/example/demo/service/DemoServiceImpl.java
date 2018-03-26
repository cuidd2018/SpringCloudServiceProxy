package com.example.demo.service;

import com.example.demo.util.FileUtil;
import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.page.PageImpl;
import com.example.demo_service_interface.page.PageRequest;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service("demoService")
public class DemoServiceImpl  implements DemoService,Serializable{

    @Override
    public String sayHello() {
//        try {
//            //TODO 这里设置时间延迟，仅为测试用！可设置为0
//            Thread.sleep(5*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "<<<<<<<<<<<<<< now access micro service success,  hello word!<<<<<<<<<<<<<<";
    }

    @Override
    public Page<DemoVO> invokeObject(PageRequest pageRequest, ThrowExceptionType throwExceptionType) throws ServiceException {
        DemoVO arg= new DemoVO();
        arg.setName("demo");
        List<DemoVO> demoVOS=new ArrayList<>();
        demoVOS.add(arg);
        Page<DemoVO> pageable=PageImpl.create(demoVOS,pageRequest,demoVOS.size());
        if(throwExceptionType.getValue().intValue()==ThrowExceptionType.THROW_EXCEPTION.getValue().intValue()){
            throw new ServiceException(ThrowExceptionType.THROW_EXCEPTION.getName());
        }
        return pageable;
    }

    @Override
    public String uploadFile(byte[] file,String name) {

        FileUtil.byte2File(file,"/mnt/file/",name);
        return "上传成功，保存："+"/mnt/file/"+name;
    }
}
