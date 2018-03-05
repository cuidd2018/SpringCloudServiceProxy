package com.example.demo_service_interface.page;

import java.util.List;

/**
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:34:02
 * @param <T>
 */
public interface Page<T> extends Pageable{
	/**
	 * 页数
	 * @return
	 */
	Integer getTotalPages();

	/**
	 * 总记录数
	 * @return
	 */
	Integer getTotal();

	/**
	 * 页码
	 * @return
	 */
	Integer getPageNum();

	List<T> getContent();

}
