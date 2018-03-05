package com.example.demo_service_interface.page;

/**
 * @author zhuxiujie
 * @since 2016年8月12日 下午1:36:28
 */
public class PageRequest extends PageBean<Object> {

    private static final long serialVersionUID = 1232825578694716871L;

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

        setPageNum(page);
        setPageSize(size);
        setOffset(countOffset(page, size));
    }

    private static Integer countOffset(Integer page, Integer size) {
        if (page == null || size == null) return null;
        return (page - 1) * size;
    }

}
