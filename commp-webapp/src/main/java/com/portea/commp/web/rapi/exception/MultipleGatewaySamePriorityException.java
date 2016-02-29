package com.portea.commp.web.rapi.exception;

import com.portea.commp.service.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class MultipleGatewaySamePriorityException extends CommpWebApplicationException {

    private static final long serialVersionUID = -8840443868709237725L;
    private final Integer firstGw;
    private final Integer secondGw;
    private final Integer priority;

    public MultipleGatewaySamePriorityException(Integer firstGw, Integer secondGw,
                                                Integer priority) {
        this.firstGw = firstGw;
        this.secondGw = secondGw;
        this.priority = priority;
    }

    public MultipleGatewaySamePriorityException(Integer firstGw, Integer secondGw,
                                                Integer priority, String message) {
        super(message);
        this.firstGw = firstGw;
        this.secondGw = secondGw;
        this.priority = priority;
    }

    public MultipleGatewaySamePriorityException(Integer firstGw, Integer secondGw,
                                                Integer priority, String message, Throwable cause) {
        super(message, cause);
        this.firstGw = firstGw;
        this.secondGw = secondGw;
        this.priority = priority;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.MULTIPLE_GATEWAY_HAVING_SAME_PRIORITY;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format("Gateway id {0} and id {1} are given same priority {2}::{0}|{1}|{2}",
                firstGw, secondGw, priority);
    }
}
