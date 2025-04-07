package com.fitech.app.users.application.controllers;

import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.model.FitnessGoalStatusDto;
import com.fitech.app.users.domain.services.FitnessGoalStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/app/fitness-goal-status")
public class FitnessGoalStatusController {

    @Autowired
    private FitnessGoalStatusService fitnessGoalStatusService;

    @PostMapping
    public ResponseEntity<FitnessGoalStatusDto> create(@RequestBody FitnessGoalStatusDto dto) {
        return ResponseEntity.ok(fitnessGoalStatusService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessGoalStatusDto> update(@PathVariable Integer id, @RequestBody FitnessGoalStatusDto dto) {
        FitnessGoalStatusDto updated = fitnessGoalStatusService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessGoalStatusDto> getById(@PathVariable Integer id) {
        FitnessGoalStatusDto dto = fitnessGoalStatusService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<ResultPage<FitnessGoalStatusDto>> getAll(Pageable paging) {
        return ResponseEntity.ok(fitnessGoalStatusService.getAll(paging));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FitnessGoalStatusDto>> getAll() {
        return ResponseEntity.ok(fitnessGoalStatusService.getAll());
    }
} 