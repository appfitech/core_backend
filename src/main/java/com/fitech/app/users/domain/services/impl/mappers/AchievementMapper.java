package com.fitech.app.users.domain.services.impl.mappers;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.users.domain.entities.Achievement;
import com.fitech.app.users.domain.model.AchievementDto;

public class AchievementMapper {

  public static Achievement toEntity(AchievementDto dto) {
    return MapperUtil.map(dto, Achievement.class);
  }

  public static AchievementDto toDto(Achievement entity) {
    return MapperUtil.map(entity, AchievementDto.class);
  }
}
