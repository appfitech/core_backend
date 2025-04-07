package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.entities.FitnessGoalStatus;
import com.fitech.app.users.domain.model.FitnessGoalStatusDto;
import com.fitech.app.users.domain.services.FitnessGoalStatusService;
import com.fitech.app.users.infrastructure.repository.FitnessGoalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FitnessGoalStatusServiceImpl implements FitnessGoalStatusService {

    @Autowired
    private FitnessGoalStatusRepository fitnessGoalStatusRepository;

    @Override
    public FitnessGoalStatusDto save(FitnessGoalStatusDto dto) {
        FitnessGoalStatus entity = MapperUtil.map(dto, FitnessGoalStatus.class);
        entity = fitnessGoalStatusRepository.save(entity);
        return MapperUtil.map(entity, FitnessGoalStatusDto.class);
    }

    @Override
    public FitnessGoalStatusDto update(Integer id, FitnessGoalStatusDto dto) {
        Optional<FitnessGoalStatus> optionalEntity = fitnessGoalStatusRepository.findById(id);
        if (optionalEntity.isPresent()) {
            FitnessGoalStatus entity = optionalEntity.get();
            MapperUtil.map(dto, entity);
            entity = fitnessGoalStatusRepository.save(entity);
            return MapperUtil.map(entity, FitnessGoalStatusDto.class);
        }
        return null;
    }

    @Override
    public FitnessGoalStatusDto getById(Integer id) {
        Optional<FitnessGoalStatus> optionalEntity = fitnessGoalStatusRepository.findById(id);
        return optionalEntity.map(entity -> MapperUtil.map(entity, FitnessGoalStatusDto.class)).orElse(null);
    }

    @Override
    public ResultPage<FitnessGoalStatusDto> getAll(Pageable paging) {
        return PaginationUtil.prepareResultWrapper(fitnessGoalStatusRepository.findAll(paging), FitnessGoalStatusDto.class);
    }

    @Override
    public List<FitnessGoalStatusDto> getAll() {
        return MapperUtil.mapAll(fitnessGoalStatusRepository.findAll(), FitnessGoalStatusDto.class);
    }
} 