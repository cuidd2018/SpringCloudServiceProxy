package com.zxj.cloud_service_proxy_core.util.enums;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.vo.ConstantVO;
import org.apache.commons.collections.map.HashedMap;

import java.util.List;
import java.util.Map;

public class EnumContext {

    private static Map<Class<? extends Constant>, List<? extends ConstantVO>> constantVOListMap = new HashedMap();
    private static Map<Class<? extends Constant>, Map<?, ? extends ConstantVO<?>>> constantVOMap = new HashedMap();
    private static Map<Class<? extends Constant>, ConstantVO[]> constantVOArrayMap = new HashedMap();


    private static Map<Class<? extends Constant>, List<? extends Constant>> enumListMap = new HashedMap();
    private static Map<Class<? extends Constant>, Map<?, ? extends Constant<?>>> enumMap = new HashedMap();
    private static Map<Class<? extends Constant>, Constant[]> enumArrayMap = new HashedMap();

    public static <T, E extends Constant<T>> List<ConstantVO<T>> getConstantVOList(Class<E> clazz) throws ServiceException {
        List<ConstantVO<T>> eList = (List<ConstantVO<T>>) constantVOListMap.get(clazz);
        if (eList == null) {
            eList = EnumScanUtil.scanConstantVOList(clazz);
            constantVOListMap.put(clazz, eList);
        }
        return eList;
    }


    public static <T, E extends Constant<T>> List<E> getEnumList(Class<E> clazz) throws ServiceException {
        List<E> eList = (List<E>) enumListMap.get(clazz);
        if (eList == null) {
            eList = EnumScanUtil.scanEnumList(clazz);
            enumListMap.put(clazz, eList);
        }
        return eList;
    }

    public static <T, E extends Constant<T>> Map<T, E> getMap(Class<E> clazz) throws ServiceException {
        Map<T, E> eMap = (Map<T, E>) enumMap.get(clazz);
        if (eMap == null) {
            eMap = EnumScanUtil.scanMap(clazz);
            enumMap.put(clazz, eMap);
        }
        return eMap;
    }

    public static <T, E extends Constant<T>> E[] getArray(Class<E> clazz) throws ServiceException {
        E[] eList = (E[]) enumArrayMap.get(clazz);
        if (eList == null) {
            eList = EnumScanUtil.scanArrayOf(clazz);
            enumArrayMap.put(clazz, eList);
        }
        return eList;
    }

    public static <T, E extends Constant<T>> Map<T, ConstantVO<T>> getConstantVOMap(Class<E> clazz) throws ServiceException {
        Map<T, ConstantVO<T>> oldMap = (Map<T, ConstantVO<T>>) constantVOMap.get(clazz);
        if (oldMap == null) {
            oldMap = EnumScanUtil.scanConstantVOMap(clazz);
            constantVOMap.put(clazz, oldMap);
        }
        return oldMap;
    }


    public static <T, E extends ConstantVO<T>> ConstantVO<T>[] getConstantVOArray(Class<E> clazz) throws ServiceException {
        ConstantVO<T>[] eList = (ConstantVO<T>[]) constantVOArrayMap.get(clazz);
        if (eList == null) {
            eList = EnumScanUtil.scanConstantVOArray(clazz);
            constantVOArrayMap.put(clazz, eList);
        }
        return eList;
    }
}
