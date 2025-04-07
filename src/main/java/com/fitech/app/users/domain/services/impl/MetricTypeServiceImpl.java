package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.entities.MetricType;
import com.fitech.app.users.domain.model.MetricTypeDto;
import com.fitech.app.users.domain.services.MetricTypeService;
import com.fitech.app.users.infrastructure.repository.MetricTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetricTypeServiceImpl implements MetricTypeService {

    @Autowired
    private MetricTypeRepository metricTypeRepository;

    @Override
    public MetricTypeDto save(MetricTypeDto dto) {
        MetricType entity = MapperUtil.map(dto, MetricType.class);
        entity = metricTypeRepository.save(entity);
        return MapperUtil.map(entity, MetricTypeDto.class);
    }

    @Override
    public MetricTypeDto update(Integer id, MetricTypeDto dto) {
        Optional<MetricType> optionalEntity = metricTypeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            MetricType entity = optionalEntity.get();
            MapperUtil.map(dto, entity);
            entity = metricTypeRepository.save(entity);
            return MapperUtil.map(entity, MetricTypeDto.class);
        }
        return null;
    }

    @Override
    public MetricTypeDto getById(Integer id) {
        Optional<MetricType> optionalEntity = metricTypeRepository.findById(id);
        return optionalEntity.map(entity -> MapperUtil.map(entity, MetricTypeDto.class)).orElse(null);
    }

    @Override
    public ResultPage<MetricTypeDto> getAll(Pageable paging) {
        return PaginationUtil.prepareResultWrapper(metricTypeRepository.findAll(paging), MetricTypeDto.class);
    }

    @Override
    public List<MetricTypeDto> getAll() {
        return MapperUtil.mapAll(metricTypeRepository.findAll(), MetricTypeDto.class);
    }
} 