package com.portea.commp.smsen.domain;

public enum SmsPrimaryProcessingState {

    LOADED_FOR_CREATION,

    CREATED_FOR_SUBMISSION,

    SUBMISSION_UNDER_PROCESS,

    SUBMISSION_COMPLETED,

    STATUS_CHECK_UNDER_PROCESS,

    STATUS_CHECK_COMPLETED

    ;

}
