package com.spring.insight.api.cases.web.rest;

import co.insight.starter.firebase.service.AuditEventServiceFireBase;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.insight.api.cases.web.rest.util.PaginationUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/management/audits")
public class AuditResource {

    private final AuditEventServiceFireBase auditEventServiceFireBase;

    public AuditResource(AuditEventServiceFireBase auditEventServiceFireBase) {
        this.auditEventServiceFireBase = auditEventServiceFireBase;
    }


    @GetMapping
    public ResponseEntity<List<AuditEvent>> getAll(@ApiParam Pageable pageable) {
        Page<AuditEvent> page = auditEventServiceFireBase.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/management/audits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping(params = {"fromDate", "toDate"})
    public ResponseEntity<List<AuditEvent>> getByDates(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        @ApiParam Pageable pageable) {

        Page<AuditEvent> page = auditEventServiceFireBase.findByDates(fromDate.atStartOfDay(ZoneId.systemDefault()).toLocalDate(), toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toLocalDate(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/management/audits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/{id:.+}")
    public ResponseEntity<AuditEvent> get(@PathVariable String id , @ApiParam Pageable pageable) {
    	Page<AuditEvent> events  = auditEventServiceFireBase.findById(id, pageable);
    	AuditEvent[] arrs = new AuditEvent[events.getSize()];
    	List<AuditEvent> list = events.getContent();
    	Optional<AuditEvent> optional = Arrays.stream(list.toArray(arrs)).findFirst();

        return ResponseUtil.wrapOrNotFound(optional);
    }
}
