package com.fitech.app.users.domain.services;

import com.fitech.app.users.application.dto.MetricTypeUnitDto;
import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.data.domain.Pageable;

public interface MetricTypeUnitService {
    MetricTypeUnitDto create(MetricTypeUnitDto metricTypeUnitDto);
    MetricTypeUnitDto update(Integer id, MetricTypeUnitDto metricTypeUnitDto);
    void delete(Integer id);
    MetricTypeUnitDto findById(Integer id);
    ResultPage<MetricTypeUnitDto> findAll(Pageable pageable);
    ResultPage<MetricTypeUnitDto> findByMetricType(Integer metricTypeId, Pageable pageable);
    boolean existsByMetricTypeAndUnitOfMeasure(Integer metricTypeId, Integer unitOfMeasureId);
} 