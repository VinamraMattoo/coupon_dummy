package com.portea.cpnen.rapi.exception;


import java.text.MessageFormat;

public class SourceNameUnrecognizedException extends CouponApplicationException {

    private static final long serialVersionUID = 3458817221486912325L;

    private String sourceName;

public SourceNameUnrecognizedException(String sourceName) {
        super();
        this.sourceName = sourceName;
    }

    public SourceNameUnrecognizedException(String sourceName, String message) {
        super(message);
        this.sourceName = sourceName;
    }

    public SourceNameUnrecognizedException(String sourceName, String message, Throwable cause) {
        super(message, cause);
        this.sourceName = sourceName;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.RESERVED_FOR_ANOTHER_USER;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Source name {0} is not recognized::{0}",
                sourceName
        );
    }
}
