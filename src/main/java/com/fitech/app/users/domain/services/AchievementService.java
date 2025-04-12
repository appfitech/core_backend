package com.fitech.app.users.domain.services;

import com.fitech.app.users.domain.model.AchievementDto;

import java.util.List;

public interface AchievementService {
  List<AchievementDto> getAchievementsByTrainerId(Long trainerId);

  AchievementDto createAchievement(Long trainerId, AchievementDto achievement);

  AchievementDto updateAchievement(Long achievementId, AchievementDto updated);

  void deleteAchievement(Long achievementId);
}
