package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class MissingGwMappingForPriorityException extends CommpWebApplicationException {

    private final Integer priority;

    public MissingGwMappingForPriorityException(Integer priority) {
        this.priority = priority;
    }

    public MissingGwMappingForPriorityException(Integer priority, String message) {
        super(message);
        this.priority = priority;
    }

    public MissingGwMappingForPriorityException(Integer priority, String message, Throwable cause) {
        super(message, cause);
        this.priority = priority;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MISSING_GATEWAY_MAPPING_FOR_PRIORITY;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("No gateway mapping found for priority {0}::{0}", priority);
    }
}
