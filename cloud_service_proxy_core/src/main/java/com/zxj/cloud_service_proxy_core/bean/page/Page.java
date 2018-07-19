package com.zxj.cloud_service_proxy_core.bean.page;

import java.util.List;

/**
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:34:02
 * @param <T>
 */
public  class Page<T> extends Pageable {
	
	private Integer	totalPages;
	
	private Integer	total;
	
	private List<T>	content;
	
	public Integer getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<T> getContent() {
		return content;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
}
