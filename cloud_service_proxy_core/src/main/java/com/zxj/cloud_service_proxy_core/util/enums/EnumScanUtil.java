package com.zxj.cloud_service_proxy_core.util.enums;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;

import java.lang.reflect.Field;
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
        List<E> enums = EnumContext.getList(clazz);
        E[] arr= (E[]) new Object[]{};
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
        List<E> enums = EnumContext.getList(clazz);
        Map<T, E> map = new HashMap<>();
        for (E e : enums) {
            map.put( e.getValue(), e);
        }
        return map;
    }

    /**
     * 扫描枚举list
     * @param clazz
     * @param <T>
     * @param <E>
     * @return
     * @throws ServiceException
     */
    public static <T,E extends Constant<T>>  List<E> scanEnumList(Class<E> clazz) throws ServiceException {
        List<E> eList=new ArrayList<>();
        List<Field> fields= scanFields(clazz);
        for (Field field:fields){
            if(field.getType().equals(clazz)){
                field.setAccessible(true);
                try {
                    eList.add((E) field.get(clazz));
                } catch (IllegalAccessException e) {
                    throw new ServiceException(e);
                }
            }
        }
        return eList;
    }

    /**
     * 扫描feild
     * @param source
     * @return
     */
    private static List<Field> scanFields(Class source) {
        List<Field> fieldList = new ArrayList();
        for(Class tempClass = source; tempClass != null; tempClass = tempClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
        }
        Iterator var3 = fieldList.iterator();
        while(var3.hasNext()) {
            Field field = (Field)var3.next();
            field.setAccessible(true);
        }
        return fieldList;
    }
}
