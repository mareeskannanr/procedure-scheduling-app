package com.study.scheduling.app.controller;

import com.study.scheduling.app.model.StudySchedule;
import com.study.scheduling.app.service.UIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UIRestCtrl {

    @Autowired
    private UIService uiService;

    @GetMapping("/rooms")
    public ResponseEntity getAllRooms() {
        return ResponseEntity.ok(uiService.getAllRooms());
    }

    @GetMapping("/doctors")
    public ResponseEntity getAllDoctors() {
        return ResponseEntity.ok(uiService.getAllDoctors());
    }

    @GetMapping("/patients")
    public ResponseEntity getAllPatients() {
        return ResponseEntity.ok(uiService.getAllPatients());
    }

    @GetMapping("/schedules")
    public ResponseEntity getAllSchedules() {
        return ResponseEntity.ok(uiService.getAllSchedules());
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity getScheduleById(@PathVariable("id") Long id) {
        StudySchedule result = uiService.getScheduleById(id);
        if(result != null) {
            return ResponseEntity.ok(uiService.getScheduleById(id));
        }

        return ResponseEntity.notFound().build();
    }

}
