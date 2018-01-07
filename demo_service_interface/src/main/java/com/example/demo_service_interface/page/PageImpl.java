package com.example.demo_service_interface.page;

import java.util.List;

/**
 * 
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:43:27
 * @param <T>
 */
public class PageImpl<T> implements Page<T>, Pageable {

	private final int		total;

	private final Pageable	pageable;

	private final List<T>	content;

	public PageImpl(List<T> content, Pageable pageable, Integer total) {
		if(total==null)total=0;
		this.content = content;
		this.pageable = pageable;
		this.total = total;
	}

	@Override
	public Integer getPageNum() {
		return this.pageable.getPageNum();
	}

	@Override
	public Integer getPageSize() {
		return this.pageable.getPageSize();
	}

	@Override
	public Integer getOffset() {
		return this.pageable.getOffset();
	}

	public Pageable getPageable() {
		return pageable;
	}

	@Override
	public int getTotalPages() {
		if (getPageSize() == 0) {
			return 1;
		}
		return (int) Math.ceil((double) this.total / (double) getPageSize());
	}

	@Override
	public Integer getTotal() {
		return this.total;
	}

	@Override
	public List<T> getContent() {
		return this.content;
	}
}
