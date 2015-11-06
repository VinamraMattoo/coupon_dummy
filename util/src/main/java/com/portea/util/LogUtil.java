package com.portea.util;

import org.slf4j.Logger;

/**
 * Contains utility methods for logging information
 */
public final class LogUtil {

    private LogUtil() {}

    public static final String ENTRY_PREFIX = "ENTERED";
    public static final String EXIT_PREFIX = "EXITED";

    /**
     * Outputs a 'ENTERED' trace log message
     * @param logger the logger to be used
     */
    public static final void entryTrace(Logger logger) {
        entryTrace(logger, "");
    }

    /**
     * Outputs a 'ENTERED' trace log message using the given message format and argument array
     * @param logger the logger to be used
     * @param format the message format to be used.
     * @param argArray the variable number of arguments to be used in the message format
     */
    public static final void entryTrace(Logger logger, String format, Object... argArray) {
        entryTrace(null, logger, format, argArray);
    }

    /**
     * Outputs a 'ENTERED' trace log message with the given method name using the given message
     * format and argument array
     *
     * @param methodName the name of the method to be added
     * @param logger the logger to be used
     */
    public static final void entryTrace(String methodName, Logger logger) {
        entryTrace(methodName, logger, "");
    }

    /**
     * Outputs a 'ENTERED' trace log message with the given method name using the given message
     * format and argument array
     *
     * @param methodName the name of the method to be added
     * @param logger the logger to be used
     * @param format the message format to be used.
     * @param argArray the variable number of arguments to be used in the message format
     */
    public static final void entryTrace(String methodName, Logger logger, String format, Object... argArray) {
        if (logger.isTraceEnabled()) {
            logger.trace(ENTRY_PREFIX
                    + ((methodName != null && methodName.trim().length() != 0) ? '@' + methodName + ' ' : ' ')
                    + format, argArray);
        }
    }

    /**
     * Outputs a 'EXITED' trace log message
     * @param logger the logger to be used
     */
    public static final void exitTrace(Logger logger) {
        exitTrace(logger, "");
    }

    /**
     * Outputs a 'EXITED' trace log message using the given message format and argument array
     * @param logger the logger to be used
     * @param format the message format to be used.
     * @param argArray the variable number of arguments to be used in the message format
     */
    public static final void exitTrace(Logger logger, String format, Object... argArray) {
        exitTrace(null, logger, format, argArray);
    }

    /**
     * Outputs a 'EXITED' trace log message with the given method name using the given message
     * format and argument array
     *
     * @param methodName the name of the method to be added
     * @param logger the logger to be used
     */
    public static final void exitTrace(String methodName, Logger logger) {
        exitTrace(methodName, logger, "");
    }

    /**
     * Outputs a 'EXITED' trace log message with the given method name using the given message
     * format and argument array
     *
     * @param methodName the name of the method to be added
     * @param logger the logger to be used
     * @param format the message format to be used.
     * @param argArray the variable number of arguments to be used in the message format
     */
    public static final void exitTrace(String methodName, Logger logger, String format, Object... argArray) {
        if (logger.isTraceEnabled()) {
            logger.trace(EXIT_PREFIX
                    + ((methodName != null && methodName.trim().length() != 0) ? '@' + methodName + ' ' : ' ')
                    + format, argArray);
        }
    }
}
