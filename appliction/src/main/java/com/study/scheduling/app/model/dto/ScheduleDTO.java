package com.study.scheduling.app.model.dto;

import com.study.scheduling.app.utils.AppConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ScheduleDTO {

    @NotNull(message = AppConstants.PATIENT_ID_REQUIRED)
    private Long patientId;

    @NotBlank(message = AppConstants.DESCRIPTION_REQUIRED)
    private String description;

    @NotNull(message = AppConstants.PLANNED_START_TIME)
    private LocalDateTime plannedStartTime;

    private LocalDateTime estimatedEndTime;

    @NotNull(message = AppConstants.DOCTOR_REQUIRED)
    private Long doctorId;

    @NotNull(message = AppConstants.ROOM_REQUIRED)
    private Long roomId;

}
