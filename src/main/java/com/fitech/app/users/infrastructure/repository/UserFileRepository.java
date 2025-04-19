package com.fitech.app.users.infrastructure.repository;

import com.fitech.app.users.domain.entities.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileRepository extends JpaRepository<UserFiles, Long> {
  List<UserFiles> findByUserId(Long userId);
  void deleteByUserId(Long userId);
  Boolean existsByUserId(Long userId);
}
