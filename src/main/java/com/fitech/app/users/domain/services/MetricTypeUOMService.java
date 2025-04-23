package com.fitech.app.users.domain.services;

import com.fitech.app.users.application.dto.MetricTypeUOMDto;
import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.data.domain.Pageable;

public interface MetricTypeUOMService {
    MetricTypeUOMDto create(MetricTypeUOMDto metricTypeUnitDto);
    MetricTypeUOMDto update(Integer id, MetricTypeUOMDto metricTypeUnitDto);
    void delete(Integer id);
    MetricTypeUOMDto findById(Integer id);
    ResultPage<MetricTypeUOMDto> findAll(Pageable pageable);
    ResultPage<MetricTypeUOMDto> findByMetricType(Integer metricTypeId, Pageable pageable);
    boolean existsByMetricTypeAndUnitOfMeasure(Integer metricTypeId, Integer unitOfMeasureId);
} 