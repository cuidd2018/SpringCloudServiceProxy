package com.example.demo_service_interface.page;

import com.zxj.cloud_service_proxy_core.exception.ServiceException;

import java.util.List;

/**
 * @param <T>
 * @author zhuxiujie
 * @since 2016年8月12日 下午2:43:27
 */
public class PageImpl<T> extends PageBean<T> implements Page<T>, Pageable {

    private static final long serialVersionUID = 642544266229652024L;

    /**
     * 构造
     * @param content
     * @param pageable
     * @param total
     * @param <T>
     * @return
     */
    public static <T> PageImpl<T> create(List<T> content, Pageable pageable, Integer total) {
        PageImpl<T> pageImpl=new PageImpl<>();
        pageImpl.setContent(content);
        pageImpl.setTotal(total==null?0:total);
        pageImpl.setPageSize(pageable.getPageSize());
        pageImpl.setOffset(pageable.getOffset());
        pageImpl.setPageNum(pageable.getPageNum());
        pageImpl.setTotalPages(countTotalPages(pageable.getPageSize(), pageImpl.getTotal()));
        return pageImpl;
    }

    /**
     * 构造
     * @param content
     * @param page
     * @param size
     * @param total
     * @param <T>
     * @return
     */
    public static <T> PageImpl<T> create(List<T> content, Integer page,Integer size, Integer total) throws ServiceException {
        Pageable pageable=PageRequest.create(page,size);
        return create(content,pageable,total);
    }

    public static int countTotalPages(int pageSize, int total) {
        if (pageSize == 0) {
            return 1;
        }
        return (int) Math.ceil((double) total / (double) pageSize);
    }
}
