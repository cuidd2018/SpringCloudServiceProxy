package com.example.demo_service_interface.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author zhuxiujie
 * @since 2016年8月12日 下午1:36:28
 */
public class PageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = 1232825578694716871L;

    private Integer page;
    private Integer size;
    private Integer offset;

    public PageRequest(Integer page, Integer size) {
        if (size != null && size == -1) {
            page = null;
            size = null;
        }
        if (page != null && page <= 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }

        if (size != null && size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        this.page = page;
        this.size = size;

        offset = countOffset(page, size);
    }

    private static Integer countOffset(Integer page, Integer size) {
        if (page == null || size == null) return null;
        return (page - 1) * size;
    }

    /**
     * 总数
     *
     * @return
     */
    @Override
    public Integer getPageSize() {
        return size;
    }

    @JsonIgnore
    public Integer getOffset() {
        return offset;
    }

    /**
     * 页码
     *
     * @return
     */
    @Override
    public Integer getPageNum() {
        return page;
    }
}
