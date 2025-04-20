package com.fitech.app.users.application.controllers;

import com.fitech.app.users.application.dto.UnitOfMeasureDto;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.services.UnitOfMeasureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/app/units-of-measure")
public class UnitOfMeasureController {

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public UnitOfMeasureController(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @PostMapping
    public ResponseEntity<UnitOfMeasureDto> create(@Valid @RequestBody UnitOfMeasureDto unitOfMeasureDto) {
        UnitOfMeasureDto createdUnit = unitOfMeasureService.create(unitOfMeasureDto);
        return new ResponseEntity<>(createdUnit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitOfMeasureDto> update(@PathVariable Long id, @Valid @RequestBody UnitOfMeasureDto unitOfMeasureDto) {
        UnitOfMeasureDto updatedUnit = unitOfMeasureService.update(id, unitOfMeasureDto);
        return ResponseEntity.ok(updatedUnit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unitOfMeasureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitOfMeasureDto> findById(@PathVariable Long id) {
        UnitOfMeasureDto unitOfMeasure = unitOfMeasureService.findById(id);
        return ResponseEntity.ok(unitOfMeasure);
    }

    @GetMapping
    public ResponseEntity<ResultPage<UnitOfMeasureDto>> findAll(Pageable pageable) {
        ResultPage<UnitOfMeasureDto> units = unitOfMeasureService.findAll(pageable);
        return ResponseEntity.ok(units);
    }
} 