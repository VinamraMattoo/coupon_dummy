package com.portea.cpnen.web.rapi.exception;

import com.portea.cpnen.rapi.exception.ExceptionalCondition;

import java.text.MessageFormat;

public class CodeCreationException extends CouponWebApplicationException {

    private static final long serialVersionUID = -6987889310284240912L;
    private String errorMsg;


    public CodeCreationException(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }

    public CodeCreationException(String errorMsg, String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    public CodeCreationException(String errorMsg, String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = errorMsg;
    }

    @Override
    public ExceptionalCondition.Error getError() {
        return ExceptionalCondition.Error.CODE_CREATION_FAILED;
    }

    @Override
    public String getExplanatoryMessage() {
        return MessageFormat.format(
                "Code creation failed: {0}",
                errorMsg
        );
    }
}