package com.zxj.cloud_service_proxy_core.util.enums;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.vo.ConstantVO;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

public class EnumScanUtil {

    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <E extends Constant> E[] scanArrayOf(Class<E> clazz) throws ServiceException {
        List<E> enums = EnumContext.getEnumList(clazz);
        E[] arr = (E[]) new Object[]{};
        return enums.toArray(arr);
    }


    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T, E extends Constant<T>> Map<T, E> scanMap(Class<E> clazz) throws ServiceException {
        List<E> enums = EnumContext.getEnumList(clazz);
        Map<T, E> map = new HashMap<>();
        for (E e : enums) {
            map.put(e.getValue(), e);
        }
        return map;
    }


    public static <T, E extends Constant<T>> Map<T, ConstantVO<T>> scanConstantVOMap(Class<E> clazz) throws ServiceException {
        Map<T, E> enumMap = EnumContext.getMap(clazz);
        Map<T, ConstantVO<T>> constantVOMap = new HashedMap();
        for (T in : enumMap.keySet()) {
            E e = enumMap.get(in);
            constantVOMap.put(in, cast(e));
        }
        return constantVOMap;
    }

    /**
     * 扫描枚举list
     *
     * @param clazz
     * @param <T>
     * @param <E>
     * @return
     * @throws ServiceException
     */
    public static <T, E extends Constant<T>> List<ConstantVO<T>> scanConstantVOList(Class<E> clazz) throws ServiceException {
        List<E> list = EnumContext.getEnumList(clazz);
        List<ConstantVO<T>> constantVOs = new ArrayList<>();
        for (E e : list) {
            constantVOs.add(cast(e));
        }
        return constantVOs;
    }

    /**
     * 扫描枚举list
     *
     * @param clazz
     * @param <T>
     * @param <E>
     * @return
     * @throws ServiceException
     */
    public static <T, E extends Constant<T>> List<E> scanEnumList(Class<E> clazz) {
        E[] enums = clazz.getEnumConstants();
        List<E> list = Arrays.asList(enums);
        return list;
    }

    private static <T, E extends Constant<T>> ConstantVO<T> cast(E e) {
        ConstantVO<T> constantVO = new ConstantVO<>();
        constantVO.setName(e.getName());
        constantVO.setValue(e.getValue());
        return constantVO;
    }

    public static <T, E extends Constant<T>> ConstantVO<T>[] scanConstantVOArray(Class<E> clazz) throws ServiceException {
        E[] es = EnumContext.getArray(clazz);
        ConstantVO<T>[] constantVOs = new ConstantVO[es.length];
        int i = 0;
        for (E e : es) {
            constantVOs[i] = cast(e);
            i++;
        }
        return constantVOs;
    }
}
