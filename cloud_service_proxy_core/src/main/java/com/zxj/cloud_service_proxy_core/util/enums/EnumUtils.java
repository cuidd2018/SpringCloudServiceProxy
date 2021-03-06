/**
 *
 */
package com.zxj.cloud_service_proxy_core.util.enums;

import com.zxj.cloud_service_proxy_core.vo.ConstantVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.constant.Constant;

import java.util.*;

/**
 * @author zhuxiujie
 * @since 2016年8月4日 下午1:27:42
 */
public class EnumUtils {


    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T, E extends Constant<T>> Map<T, ConstantVO<T>> toConstantVOMap(Class<E> clazz) throws ServiceException {
        return EnumContext.getConstantVOMap(clazz);
    }

    /**
     * 返回 enum 常量的list
     *
     * @param clazz
     * @return
     */
    public static <T, E extends Constant<T>> List<ConstantVO<T>> toConstantVOList(Class<E> clazz) throws ServiceException {
        return EnumContext.getConstantVOList(clazz);
    }


    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T, E extends ConstantVO<T>> ConstantVO<T>[] toConstantVOArray(Class<E> clazz) throws ServiceException {
        return EnumContext.getConstantVOArray(clazz);
    }


    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T, E extends ConstantVO<T>> E[] arrayOf(Class<E> clazz) throws ServiceException {
        return EnumContext.getArray(clazz);
    }


    /**
     * 返回 enum 常量的map
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T, E extends Constant<T>> Map<T, E> mapOf(Class<E> clazz) throws ServiceException {
        return EnumContext.getMap(clazz);
    }


    /**
     * 返回 enum 常量的VariableVO
     *
     * @return
     */
    public static <E extends Constant> ConstantVO constantVO(E e) {
        ConstantVO variableVO = new ConstantVO();
        variableVO.setName(e.getName());
        variableVO.setValue(e.getValue());
        return variableVO;
    }

    /**
     * 根据enum 值转 enum 对象
     *
     * @param value
     * @param eClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E extends Constant<T>> E toEnum(T value, Class<E> eClass) throws ServiceException {
        if (value == null) return null;
        Map<T, E> map = mapOf(eClass);
        E e = map.get(value);
        if (e != null) {
            return e;
        } else {
            throw new ServiceException("类型转换失败！该value值在枚举中不存在！value=", value, ",class=", eClass);
        }
    }


    public static <T, E extends Constant<T>> T getValue(E enumObject) {
        if (enumObject == null) return null;
        Constant<T> enumConstant = enumObject;
        return enumConstant.getValue();
    }

    public static <T, E extends Constant<T>> T getName(E enumObject) {
        if (enumObject == null) return null;
        Constant<T> enumConstant = enumObject;
        return enumConstant.getValue();
    }
}
