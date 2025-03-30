package com.fitech.app.commons.util;
import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.data.domain.Page;

public class PaginationUtil {
    public static<P,E> ResultPage<P> prepareResultWrapper(Page<E> resultPages, Class<P> pojoClass) {
        ResultPage<P> resultPageWrapper = new ResultPage<>();
        resultPageWrapper.setPagesResult(MapperUtil.mapAll(resultPages.getContent(), pojoClass));
        resultPageWrapper.setCurrentPage(resultPages.getNumber());
        resultPageWrapper.setTotalItems(resultPages.getTotalElements());
        resultPageWrapper.setTotalPages(resultPages.getTotalPages());
        return resultPageWrapper;
    }

    public static<P,E> ResultPage<P> prepareCustomWrapper(Page<E> resultPages, Class<P> pojoClass) {
        ResultPage<P> resultPageWrapper = new ResultPage<>();
        resultPageWrapper.setCurrentPage(resultPages.getNumber());
        resultPageWrapper.setTotalItems(resultPages.getTotalElements());
        resultPageWrapper.setTotalPages(resultPages.getTotalPages());
        return resultPageWrapper;
    }
}
