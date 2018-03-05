package com.example.demo_service_interface.page;

import java.util.List;

/**
 * @param <T>
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:43:27
 */
public class PageImpl<T> extends PageBean<T> implements Page<T>, Pageable {

    private static final long serialVersionUID = 642544266229652024L;

    private Pageable pageable;

    public PageImpl(List<T> content, Pageable pageable, Integer total) {
        setContent(content);
        this.pageable = pageable;
        setTotal(total);

        if (total == null) setTotal(0);
        setPageSize(pageable.getPageSize());
        setOffset(pageable.getOffset());
        setPageNum(pageable.getPageNum());
        setTotalPages(countTotalPages(getPageSize(), getTotal()));
    }

    private static int countTotalPages(int pageSize, int total) {
        if (pageSize == 0) {
            return 1;
        }
        return (int) Math.ceil((double) total / (double) pageSize);
    }

    public Pageable getPageable() {
        return pageable;
    }
}
