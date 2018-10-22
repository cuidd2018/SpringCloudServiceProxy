package com.example.demo.service;

import com.example.demo.util.FileUtil;
import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.example.demo_service_interface.vo.TestVO;
import com.zxj.cloud_service_proxy_core.bean.page.Page;
import com.zxj.cloud_service_proxy_core.bean.page.PageImpl;
import com.zxj.cloud_service_proxy_core.bean.page.PageRequest;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.vo.ConstantVO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("demoService")
public class DemoServiceImpl  implements DemoService,Serializable{

    @Override
    public String sayHello( List<ThrowExceptionType> list){

        //TODO 这里设置时间延迟，仅为测试用！可设置为0
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "<<<<<<<<<<<<<< now access micro service success,  hello word!<<<<<<<<<<<<<<"+list.get(0).getName()+list.get(0).getValue();
    }

    @Override
    public Page<DemoVO> invokeObject(PageRequest pageRequest, ThrowExceptionType throwExceptionType ,ConstantVO constantVO,
                                     List<ServiceProxyErrorCode> serviceProxyErrorCodes,
                                     Map<String,List<ServiceProxyErrorCode>> serviceProxyErrorCodeMap) throws ServiceException {
        TestVO testVO=new TestVO();
        testVO.setTest("fffssssggg");
        DemoVO arg= new DemoVO();
        arg.setName("demo");
        arg.setTestVO(testVO);
        List demoVOS=new ArrayList<DemoVO>();
        demoVOS.add(arg);
        List testVOS=new ArrayList<TestVO>();
        testVOS.add(testVO);
        arg.setTestVOs(testVOS);
        Map testVOMap=new HashMap<String,TestVO>();
        testVOMap.put("test",testVO);
        arg.setTestVOMap(testVOMap);
        Page pageable=PageImpl.create(demoVOS,pageRequest,demoVOS.size());

        serviceProxyErrorCodes.get(0).getName();
        serviceProxyErrorCodeMap.get("error").get(0).getName();

        Object value= constantVO.getValue();

        if(throwExceptionType.getValue().intValue()==ThrowExceptionType.table.THROW_EXCEPTION.getValue().intValue()){
            throw new ServiceException(ThrowExceptionType.table.THROW_EXCEPTION.getName());
        }
        return pageable;
    }

    @Override
    public String uploadFile(byte[] file,String name) {

        FileUtil.byte2File(file,"/mnt/file/",name);
        return "上传成功，保存："+"/mnt/file/"+name;
    }

    @Override
    public List<String> list() {
        List list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        return list;
    }
}
