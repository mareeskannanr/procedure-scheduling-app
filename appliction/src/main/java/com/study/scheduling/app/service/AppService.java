package com.study.scheduling.app.service;

import com.study.scheduling.app.exception.AppException;
import com.study.scheduling.app.model.*;
import com.study.scheduling.app.model.dto.ScheduleDTO;
import com.study.scheduling.app.model.dto.StatusDTO;

public interface AppService {

    Patient savePatient(Patient patient);

    StudySchedule saveSchedule(ScheduleDTO schedule) throws AppException;

    void updateStatus(StatusDTO statusDTO);

}
