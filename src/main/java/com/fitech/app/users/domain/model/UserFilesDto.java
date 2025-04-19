package com.fitech.app.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilesDto implements Serializable {
  private Long id;
  private Long userId;
  private String fileName;
  private String fileType;
  private String filePath;
  private LocalDateTime uploadedAt;
}
