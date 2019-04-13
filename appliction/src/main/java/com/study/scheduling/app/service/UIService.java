package com.study.scheduling.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.scheduling.app.model.Doctor;
import com.study.scheduling.app.model.Patient;
import com.study.scheduling.app.model.Room;
import com.study.scheduling.app.model.StudySchedule;

import java.util.List;

public interface UIService {

    List<Room> getAllRooms();

    List<Doctor> getAllDoctors();

    List<Patient> getAllPatients();

    List<String> getAllSchedules();

    StudySchedule getScheduleById(Long id);

}