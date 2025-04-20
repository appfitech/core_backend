package com.fitech.app.users.domain.services;

import com.fitech.app.users.application.dto.UnitOfMeasureDto;

import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.data.domain.Pageable;

public interface UnitOfMeasureService {
    UnitOfMeasureDto create(UnitOfMeasureDto unitOfMeasureDto);
    UnitOfMeasureDto update(Long id, UnitOfMeasureDto unitOfMeasureDto);
    void delete(Long id);
    UnitOfMeasureDto findById(Long id);
    ResultPage<UnitOfMeasureDto> findAll(Pageable pageable);
    boolean existsByName(String name);
    boolean existsBySymbol(String symbol);
} 