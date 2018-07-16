package com.example.demo_service_interface.page;

import java.util.List;

/**
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:34:02
 * @param <T>
 */
public abstract class Page<T> extends Pageable{
	/**
	 * 页数
	 * @return
	 */
	public abstract 	Integer getTotalPages();

	/**
	 * 总记录数
	 * @return
	 */
	public abstract	Integer getTotal();

	/**
	 * 页码
	 * @return
	 */
	public abstract	Integer getPageNum();

	/**
	 * 实体数据
	 * @return
	 */
	public abstract	List<T> getContent();

}
