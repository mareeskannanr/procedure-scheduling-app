package com.study.scheduling.app.model.dto;

import com.study.scheduling.app.model.Status;
import com.study.scheduling.app.utils.AppConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StatusDTO {

    @NotNull(message = AppConstants.STUDY_ID_REQUIRED)
    private Long studyId;

    @NotNull(message = AppConstants.STATUS_ID_REQUIRED)
    private Status status;

}
