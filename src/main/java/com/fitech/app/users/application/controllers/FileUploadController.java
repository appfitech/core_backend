package com.fitech.app.users.application.controllers;

import com.fitech.app.users.application.exception.StorageFileNotFoundException;
import com.fitech.app.users.domain.model.UserFilesDto;
import com.fitech.app.users.domain.services.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/app/file-upload")
public class FileUploadController {

  @Autowired
  private FileUploadService fileUploadService;

  @PostMapping("/user/{id}")
  public ResponseEntity<UserFilesDto> uploadFile(
    @PathVariable("id") Long userId,
    @RequestParam("file") MultipartFile file
  ) throws IOException {
    UserFilesDto response = this.fileUploadService.uploadFile(userId, file);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<List<UserFilesDto>> getAllByUser(
    @PathVariable("id") Long userId
  ) {
    List<UserFilesDto> response = this.fileUploadService.getAllByUser(userId);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<Object> deleteByUserId(
    @PathVariable("id") Long userId
  ) {
    this.fileUploadService.deleteByUserId(userId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<Object> deleteByFileId(
    @PathVariable("fileId") Long fileId
  ) {
    this.fileUploadService.deleteByFileId(fileId);
    return ResponseEntity.noContent().build();
  }


}
