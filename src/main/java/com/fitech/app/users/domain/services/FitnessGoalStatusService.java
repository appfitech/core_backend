package com.fitech.app.users.domain.services;

import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.model.FitnessGoalStatusDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FitnessGoalStatusService {
    FitnessGoalStatusDto save(FitnessGoalStatusDto dto);
    FitnessGoalStatusDto update(Integer id, FitnessGoalStatusDto dto);
    FitnessGoalStatusDto getById(Integer id);
    ResultPage<FitnessGoalStatusDto> getAll(Pageable paging);
    List<FitnessGoalStatusDto> getAll();
} 