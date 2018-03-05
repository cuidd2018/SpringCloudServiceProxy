package com.example.demo_service_interface.page;

import java.util.List;

/**
 * @param <T>
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:43:27
 */
public class PageImpl<T> implements Page<T>, Pageable {

    private Integer total;

    private Pageable pageable;

    private List<T> content;

    private Integer totalPages;

    private Integer pageSize;

    private Integer offset;

    private Integer pageNum;

    public PageImpl(List<T> content, Pageable pageable, Integer total) {
        this.content = content;
        this.pageable = pageable;
        this.total = total;

        if (total == null) this.total = 0;
        this.pageSize=pageable.getPageSize();
        this.offset=pageable.getOffset();
        this.pageNum=pageable.getPageNum();
        totalPages = countTotalPages(getPageSize(), getTotal());
    }

    private static int countTotalPages(int pageSize, int total) {
        if (pageSize == 0) {
            return 1;
        }
        return (int) Math.ceil((double) total / (double) pageSize);
    }

    @Override
    public Integer getPageNum() {
        return this.pageNum;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Integer getTotalPages() {
        return this.totalPages;
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
