package com.study.scheduling.app.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.scheduling.app.model.Doctor;
import com.study.scheduling.app.model.Patient;
import com.study.scheduling.app.model.Room;
import com.study.scheduling.app.model.StudySchedule;
import com.study.scheduling.app.repository.*;
import com.study.scheduling.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * This class contains end point to support UI related functionalities like Doctor, Room DropDowns, Patients, Schedules Tables
 */

@Service
public class UIServiceImpl implements UIService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<String> getAllSchedules() {
        return scheduleRepository.getAllSchedules();
    }

    @Override
    public StudySchedule getScheduleById(Long id) {
        StudySchedule studySchedule = null;
        Optional<StudySchedule> scheduleOptional = scheduleRepository.findById(id);
        if(scheduleOptional.isPresent()) {
            studySchedule = scheduleOptional.get();
        }

        return studySchedule;
    }

}
