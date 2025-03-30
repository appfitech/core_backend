package com.fitech.app.commons.application.controllers;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;
public abstract class BaseController {
    protected <T> Map<String, Object> prepareResponse(ResultPage<T> resultPageWrapper) {
        Map<String, Object> response = new HashMap<>();
        response.put(getResource(), resultPageWrapper.getPagesResult());
        response.put("currentPage", resultPageWrapper.getCurrentPage());
        response.put("totalItems", resultPageWrapper.getTotalItems());
        response.put("totalPages", resultPageWrapper.getTotalPages());
        return response;
    }
    protected abstract String getResource();
}
