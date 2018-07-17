package com.example.demo_service_interface.page;

import java.io.Serializable;

/**
 * Abstract interface for pagination information.
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午1:34:05
 */
public  class Pageable implements Serializable{

	private Integer pageSize;

	private Integer offset;

	private Integer pageNum;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
