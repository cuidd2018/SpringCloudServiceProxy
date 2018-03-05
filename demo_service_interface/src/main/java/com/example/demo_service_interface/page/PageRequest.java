package com.example.demo_service_interface.page;

import com.zxj.cloud_service_proxy_core.exception.ServiceException;

/**
 * @author zhuxiujie
 * @since 2016年8月12日 下午1:36:28
 */
public class PageRequest extends PageBean<Object> {

    private static final long serialVersionUID = 1232825578694716871L;

    public static PageRequest create(Integer page, Integer size) throws ServiceException {
        if (size != null && size == -1) {
            page = null;
            size = null;
        }
        if (page != null && page <= 0) {
            throw new ServiceException("Page index must not be <= 0!");
        }
        if (size != null && size < 1) {
            throw new ServiceException("Page size must not be < 1!");
        }
        PageRequest pageRequest=new PageRequest();
        pageRequest.setPageNum(page);
        pageRequest.setPageSize(size);
        pageRequest.setOffset(countOffset(page, size));
        return pageRequest;
    }

    private static Integer countOffset(Integer page, Integer size) {
        if (page == null || size == null) return null;
        return (page - 1) * size;
    }

}
