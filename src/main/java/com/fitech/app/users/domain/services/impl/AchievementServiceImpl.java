package com.fitech.app.users.domain.services.impl;

import com.fitech.app.users.application.exception.EntityNotFoundException;
import com.fitech.app.users.domain.entities.Achievement;
import com.fitech.app.users.domain.model.AchievementDto;
import com.fitech.app.users.domain.services.AchievementService;
import com.fitech.app.users.domain.services.impl.mappers.AchievementMapper;
import com.fitech.app.users.infrastructure.repository.AchievementRepository;
import com.fitech.app.users.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AchievementServiceImpl implements AchievementService {

  @Autowired
  private AchievementRepository achievementRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<AchievementDto> getAchievementsByTrainerId(Long trainerId) {

    List<Achievement> achievementsEntity = achievementRepository.findByTrainerId(trainerId);

    return achievementsEntity.stream().map(AchievementMapper::toDto).toList();
  }

  @Override
  public AchievementDto createAchievement(Long trainerId, AchievementDto dto) {
    log.info("Achievement created with trainer id {} and AchievementDto {}", trainerId, dto);
    dto.setTrainerId(trainerId);

    Achievement achievementEntity = this.achievementRepository.save(AchievementMapper.toEntity(dto));
    return AchievementMapper.toDto(achievementEntity);
  }

  @Override
  @Transactional
  public AchievementDto updateAchievement(Long achievementId, AchievementDto updated) {
    log.info("Achievement update with achievementId id {} and AchievementDto {}", achievementId, updated);
    if (!achievementRepository.existsById(achievementId)) {
      throw new EntityNotFoundException("Achievement not found with ID: " + achievementId);
    }
    Integer userId = Integer.parseInt(updated.getTrainerId().toString());
    log.info("trainerId {}", userId);
    if (!userRepository.existsById(userId)) {
      throw new EntityNotFoundException("trainer not found with ID: " + userId);
    }

    updated.setId(achievementId);
    Achievement achievementEntity = achievementRepository.save(AchievementMapper.toEntity(updated));

    return AchievementMapper.toDto(achievementEntity);
  }

  @Override
  public void deleteAchievement(Long achievementId) {
    if (!achievementRepository.existsById(achievementId)) {
      throw new EntityNotFoundException("Achievement not found with ID: " + achievementId);
    }
    achievementRepository.deleteById(achievementId);
  }
}
