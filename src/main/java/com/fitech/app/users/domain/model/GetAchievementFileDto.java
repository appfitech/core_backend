package com.fitech.app.users.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitech.app.users.domain.entities.Achievement;
import com.fitech.app.users.domain.entities.UserFiles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class GetAchievementFileDto {
  private Long id;
  private UserFiles userFile;
  private LocalDateTime uploadedAt;
}
