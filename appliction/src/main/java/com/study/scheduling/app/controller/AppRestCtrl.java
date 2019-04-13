package com.study.scheduling.app.controller;

import com.study.scheduling.app.exception.AppException;
import com.study.scheduling.app.model.Patient;
import com.study.scheduling.app.model.dto.ScheduleDTO;
import com.study.scheduling.app.model.dto.StatusDTO;
import com.study.scheduling.app.service.AppService;
import com.study.scheduling.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AppRestCtrl {

    @Autowired
    private AppService appService;

    @PostMapping("/patient")
    public ResponseEntity savePatient(@Valid @RequestBody Patient patient) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(appService.savePatient(patient));
    }

    @PostMapping("/schedule")
    public ResponseEntity saveSchedule(@Valid @RequestBody ScheduleDTO schedule) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(appService.saveSchedule(schedule));
    }

    @PostMapping("/status")
    public ResponseEntity updateStatus(@Valid @RequestBody StatusDTO status) {
        appService.updateStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(AppConstants.UPDATE_SUCCESSFUL);
    }

}
