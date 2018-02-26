/**
 *
 */
package com.zxj.cloud_service_proxy_core.util.enums;
import com.zxj.cloud_service_proxy_core.vo.ConstantVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.variable.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static <E extends Enum<? extends Constant>> E[] arrayOf(Class<E> clazz) {
		E[] enums = clazz.getEnumConstants();
		return enums;
	}

	/**
	 * 判断 常量值是否存在
	 *
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <E extends Enum<? extends Constant>> boolean exists(Class<E> clazz, String value) {
		E[] enums = clazz.getEnumConstants();
		for (Enum e : enums) {
			Object v = ((Constant) e).getValue();
			if (v.equals(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 返回 enum 常量的map
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <E extends Enum<? extends Constant>> Map<String, Object> valueOf(Class<E> clazz) {
		E[] enums = clazz.getEnumConstants();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Enum e : enums) {
			map.put(((Constant) e).getName(), ((Constant) e).getValue());
		}
		return map;
	}

	/**
	 * 返回 enum 常量的map
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <E extends Enum<? extends Constant>> Map<Object, String> valueOfObject(Class<E> clazz) {
		E[] enums = clazz.getEnumConstants();
		Map<Object, String> map = new HashMap<Object, String>();
		for (Enum e : enums) {
			map.put(((Constant) e).getValue(),((Constant) e).getName());
		}
		return map;
	}


	/**
	 * 返回 enum 常量的map
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T,E extends Enum<? extends Constant<T>>> Map<T, E> valueOfEnum(Class<E> clazz) {
		E[] enums = clazz.getEnumConstants();
		Map<T, E> map = new HashMap<>();
		for (E e : enums) {
			map.put(((Constant<T>) e).getValue(),e);
		}
		return map;
	}

	/**
	 * 返回 enum 常量的list
	 *
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<? extends Constant>> List<ConstantVO> listOf(Class<E> clazz) {
		E[] enums = clazz.getEnumConstants();
		List<ConstantVO> list = new ArrayList<>();
		for (Enum e : enums) {
			ConstantVO variableVO= new ConstantVO();
			variableVO.setName(((Constant) e).getName());
			variableVO.setValue(((Constant) e).getValue());
			list.add(variableVO);
		}
		return list;
	}

	/**
	 * 返回 enum 常量的VariableVO
	 *
	 * @return
	 */
	public static <E extends Enum<? extends Constant>> ConstantVO variableOf(E e) {
			ConstantVO variableVO= new ConstantVO();
			variableVO.setName(((Constant) e).getName());
			variableVO.setValue(((Constant) e).getValue());
		return variableVO;
	}

	/**
	 * 根据enum 值转 enum 对象
	 * @param value
	 * @param eClass
	 * @param <T>
	 * @param <E>
	 * @return
	 * @throws ServiceException
	 */
	public static  <T,E extends Enum<? extends Constant<T>>> E enumValueOf(T value, Class<E> eClass) throws ServiceException {
		if(value==null)throw new ServiceException("value can not be null!");
		Map<T, E> map= valueOfEnum(eClass);
		E e=map.get(value);
		if(e!=null){
			return e;
		}else{
			throw new ServiceException("类型转换失败！Integer value:",value,",class:",eClass);
		}
	}
}
