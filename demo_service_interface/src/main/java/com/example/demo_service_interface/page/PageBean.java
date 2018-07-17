package com.example.demo_service_interface.page;


public  class PageBean<T> extends Page<T>{

    private static final long serialVersionUID = 6599732285896151358L;

    private Integer pageSize;

    private Integer offset;

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
