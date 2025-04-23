package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.application.dto.MetricTypeUnitDto;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.entities.MetricType;
import com.fitech.app.users.domain.entities.MetricTypeUOM;
import com.fitech.app.users.domain.entities.UnitOfMeasure;
import com.fitech.app.users.domain.services.MetricTypeUnitService;
import com.fitech.app.users.infrastructure.repository.MetricTypeRepository;
import com.fitech.app.users.infrastructure.repository.MetricTypeUnitRepository;
import com.fitech.app.users.infrastructure.repository.UnitOfMeasureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetricTypeUnitServiceImpl implements MetricTypeUnitService {

    private final MetricTypeUnitRepository metricTypeUnitRepository;
    private final MetricTypeRepository metricTypeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public MetricTypeUnitServiceImpl(
            MetricTypeUnitRepository metricTypeUnitRepository,
            MetricTypeRepository metricTypeRepository,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.metricTypeUnitRepository = metricTypeUnitRepository;
        this.metricTypeRepository = metricTypeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public MetricTypeUnitDto create(MetricTypeUnitDto metricTypeUnitDto) {
        if (existsByMetricTypeAndUnitOfMeasure(metricTypeUnitDto.getMetricTypeId(), metricTypeUnitDto.getUnitOfMeasureId())) {
            throw new IllegalArgumentException("Ya existe una relación entre este tipo de métrica y unidad de medida");
        }

        MetricType metricType = metricTypeRepository.findById(metricTypeUnitDto.getMetricTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de métrica no encontrado"));
        
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(metricTypeUnitDto.getUnitOfMeasureId())
                .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));

        MetricTypeUOM metricTypeUnit = new MetricTypeUOM();
        metricTypeUnit.setMetricType(metricType);
        metricTypeUnit.setUnitOfMeasure(unitOfMeasure);
        metricTypeUnit.setDefault(metricTypeUnitDto.isDefault());

        MetricTypeUOM saved = metricTypeUnitRepository.save(metricTypeUnit);
        return convertToDto(saved);
    }

    @Override
    @Transactional
    public MetricTypeUnitDto update(Integer id, MetricTypeUnitDto metricTypeUnitDto) {
        MetricTypeUOM existing = metricTypeUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación tipo de métrica - unidad de medida no encontrada"));

        if (!existing.getMetricType().getId().equals(metricTypeUnitDto.getMetricTypeId()) ||
            !existing.getUnitOfMeasure().getId().equals(metricTypeUnitDto.getUnitOfMeasureId())) {
            if (existsByMetricTypeAndUnitOfMeasure(metricTypeUnitDto.getMetricTypeId(), metricTypeUnitDto.getUnitOfMeasureId())) {
                throw new IllegalArgumentException("Ya existe una relación entre este tipo de métrica y unidad de medida");
            }
        }

        MetricType metricType = metricTypeRepository.findById(metricTypeUnitDto.getMetricTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de métrica no encontrado"));
        
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(metricTypeUnitDto.getUnitOfMeasureId())
                .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));

        existing.setMetricType(metricType);
        existing.setUnitOfMeasure(unitOfMeasure);
        existing.setDefault(metricTypeUnitDto.isDefault());

        MetricTypeUOM updated = metricTypeUnitRepository.save(existing);
        return convertToDto(updated);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!metricTypeUnitRepository.existsById(id)) {
            throw new EntityNotFoundException("Relación tipo de métrica - unidad de medida no encontrada");
        }
        metricTypeUnitRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MetricTypeUnitDto findById(Integer id) {
        MetricTypeUOM metricTypeUnit = metricTypeUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación tipo de métrica - unidad de medida no encontrada"));
        return convertToDto(metricTypeUnit);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultPage<MetricTypeUnitDto> findAll(Pageable pageable) {
        Page<MetricTypeUOM> unitsPage = metricTypeUnitRepository.findAll(pageable);
        return PaginationUtil.prepareResultWrapper(unitsPage, MetricTypeUnitDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultPage<MetricTypeUnitDto> findByMetricType(Integer metricTypeId, Pageable pageable) {
        Page<MetricTypeUOM> unitsPage = metricTypeUnitRepository.findByMetricTypeId(metricTypeId, pageable);
        return PaginationUtil.prepareResultWrapper(unitsPage, MetricTypeUnitDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMetricTypeAndUnitOfMeasure(Integer metricTypeId, Integer unitOfMeasureId) {
        return metricTypeUnitRepository.existsByMetricTypeIdAndUnitOfMeasureId(metricTypeId, unitOfMeasureId);
    }

    private MetricTypeUnitDto convertToDto(MetricTypeUOM metricTypeUnit) {
        MetricTypeUnitDto dto = new MetricTypeUnitDto();
        dto.setId(metricTypeUnit.getId());
        dto.setMetricTypeId(metricTypeUnit.getMetricType() != null ? metricTypeUnit.getMetricType().getId() : null);
        dto.setUnitOfMeasureId(metricTypeUnit.getUnitOfMeasure() != null ? metricTypeUnit.getUnitOfMeasure().getId() : null);
        dto.setDefault(metricTypeUnit.isDefault());
        return dto;
    }
} 