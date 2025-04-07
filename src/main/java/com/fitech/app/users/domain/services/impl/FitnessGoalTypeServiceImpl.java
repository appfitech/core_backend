package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.entities.FitnessGoalType;
import com.fitech.app.users.domain.model.FitnessGoalTypeDto;
import com.fitech.app.users.domain.services.FitnessGoalTypeService;
import com.fitech.app.users.infrastructure.repository.FitnessGoalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FitnessGoalTypeServiceImpl implements FitnessGoalTypeService {

    @Autowired
    private FitnessGoalTypeRepository fitnessGoalTypeRepository;


    @Override
    public FitnessGoalTypeDto save(FitnessGoalTypeDto dto) {
        FitnessGoalType entity = MapperUtil.map(dto, FitnessGoalType.class);
        entity = fitnessGoalTypeRepository.save(entity);
        return MapperUtil.map(entity, FitnessGoalTypeDto.class);
    }

    @Override
    public FitnessGoalTypeDto update(Integer id, FitnessGoalTypeDto dto) {
        Optional<FitnessGoalType> optionalEntity = fitnessGoalTypeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            FitnessGoalType entity = optionalEntity.get();
            MapperUtil.map(dto, entity);
            entity = fitnessGoalTypeRepository.save(entity);
            return MapperUtil.map(entity, FitnessGoalTypeDto.class);
        }
        return null;
    }

    @Override
    public FitnessGoalTypeDto getById(Integer id) {
        Optional<FitnessGoalType> optionalEntity = fitnessGoalTypeRepository.findById(id);
        return optionalEntity.map(entity -> MapperUtil.map(entity, FitnessGoalTypeDto.class)).orElse(null);
    }

    @Override
    public ResultPage<FitnessGoalTypeDto> getAll(Pageable paging) {
        return PaginationUtil.prepareResultWrapper(fitnessGoalTypeRepository.findAll(paging), FitnessGoalTypeDto.class);
    }

    @Override
    public List<FitnessGoalTypeDto> getAll() {
        return MapperUtil.mapAll(fitnessGoalTypeRepository.findAll(), FitnessGoalTypeDto.class);
    }
} 