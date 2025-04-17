package com.fitech.app.users.domain.services;

import com.fitech.app.users.domain.model.UserFilesDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {
  UserFilesDto uploadFile(Long userId, MultipartFile file) throws IOException;

  List<UserFilesDto> getAllByUser(Long userId);

  void deleteByUserId(Long userId);
  void deleteByFileId(Long fileId);
}
