package com.study.scheduling.app.service;

import com.study.scheduling.app.exception.AppException;
import com.study.scheduling.app.model.*;
import com.study.scheduling.app.model.dto.ScheduleDTO;
import com.study.scheduling.app.model.dto.StatusDTO;
import com.study.scheduling.app.repository.*;
import com.study.scheduling.app.utils.AppConstants;
import com.study.scheduling.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AppServiceImpl implements AppService {

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
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public StudySchedule saveSchedule(ScheduleDTO schedule) throws AppException {

        //Validate date ranges
        if(schedule.getEstimatedEndTime() != null && ChronoUnit.MINUTES.between(schedule.getPlannedStartTime(), schedule.getEstimatedEndTime()) <= 0) {
            throw new AppException(AppConstants.TIME_DIFF_ERROR);
        }

        Patient patient = CommonUtils.getPatient(schedule, patientRepository);

        Doctor doctor = CommonUtils.getDoctor(schedule, doctorRepository);

        Room room = CommonUtils.getRoom(schedule, roomRepository);

        Study study = new Study();
        study.setPatient(patient);
        study.setDescription(schedule.getDescription());
        study.setPlannedStartTime(schedule.getPlannedStartTime());
        study.setEstimatedEndTime(schedule.getEstimatedEndTime());
        study.setStatus(Status.PLANNED);
        studyRepository.save(study);

        StudySchedule studySchedule = new StudySchedule();
        studySchedule.setStudy(study);
        studySchedule.setDoctor(doctor);
        studySchedule.setRoom(room);

        return scheduleRepository.save(studySchedule);
    }

    @Override
    public void updateStatus(StatusDTO statusDTO) {
        //Check for Study Info existence
        Optional<Study> studyOptional = studyRepository.findById(statusDTO.getStudyId());
        if(!studyOptional.isPresent()) {
            throw new AppException(AppConstants.STUDY_NOT_EXISTS);
        }

        Study study = studyOptional.get();
        study.setStatus(statusDTO.getStatus());
        studyRepository.save(study);
    }

}
