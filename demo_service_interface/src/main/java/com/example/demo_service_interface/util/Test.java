package com.example.demo_service_interface.util;

import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.zxj.cloud_service_proxy_core.util.convert.ConvertUtil;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;
import com.zxj.cloud_service_proxy_core.vo.ConstantVO;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        List<ConstantVO<Integer>> constantVOS = EnumUtils.toConstantVOList(ThrowExceptionType.class);
        System.out.println(constantVOS);

        List<ThrowExceptionType> status = new ArrayList<>();
        status.add(ThrowExceptionType.table.NOT_THROW);
        status.add(ThrowExceptionType.table.THROW_EXCEPTION);
        String s = ConvertUtil.getEncoder().encoder(status);
        System.out.println(s);


        List<ThrowExceptionType> obj = (List<ThrowExceptionType>) ConvertUtil.getDecoder().decoder(s, status.getClass());
        System.out.println(obj.get(0).getName());
        System.out.println(obj);
    }
}
