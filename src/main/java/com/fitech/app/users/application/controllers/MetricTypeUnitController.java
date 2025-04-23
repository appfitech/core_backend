package com.fitech.app.users.application.controllers;

import com.fitech.app.commons.application.controllers.BaseController;
import com.fitech.app.users.application.dto.MetricTypeUnitDto;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.services.MetricTypeUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/app/metric-type-units")
public class MetricTypeUnitController extends BaseController {

    private final MetricTypeUnitService metricTypeUnitService;

    @Autowired
    public MetricTypeUnitController(MetricTypeUnitService metricTypeUnitService) {
        this.metricTypeUnitService = metricTypeUnitService;
    }

    @PostMapping
    public ResponseEntity<MetricTypeUnitDto> create(@Valid @RequestBody MetricTypeUnitDto metricTypeUnitDto) {
        MetricTypeUnitDto created = metricTypeUnitService.create(metricTypeUnitDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricTypeUnitDto> update(@PathVariable Integer id, @Valid @RequestBody MetricTypeUnitDto metricTypeUnitDto) {
        MetricTypeUnitDto updated = metricTypeUnitService.update(id, metricTypeUnitDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        metricTypeUnitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricTypeUnitDto> findById(@PathVariable Integer id) {
        MetricTypeUnitDto metricTypeUnit = metricTypeUnitService.findById(id);
        return ResponseEntity.ok(metricTypeUnit);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        ResultPage<MetricTypeUnitDto> resultPageWrapper = metricTypeUnitService.findAll(paging);
        Map<String, Object> response = prepareResponse(resultPageWrapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-metric-type/{metricTypeId}")
    public ResponseEntity<Map<String, Object>> findByMetricType(
            @PathVariable Integer metricTypeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        ResultPage<MetricTypeUnitDto> resultPageWrapper = metricTypeUnitService.findByMetricType(metricTypeId, paging);
        Map<String, Object> response = prepareResponse(resultPageWrapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected String getResource() {
        return "metricTypeUnits";
    }
} 