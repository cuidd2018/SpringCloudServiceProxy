package com.example.demo_service_interface.page;

import java.io.Serializable;

/**
 * Abstract interface for pagination information.
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午1:34:05
 */
public abstract class Pageable implements Serializable{

	public abstract Integer getPageNum();

	public abstract Integer getPageSize();

	public abstract Integer getOffset();
}
