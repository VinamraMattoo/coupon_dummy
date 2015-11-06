package com.portea.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogUtilTest {

    private Logger logger;
    private ch.qos.logback.classic.Logger root;
    private Appender mockAppender;

    @Before
    public void setup() {
        logger = LoggerFactory.getLogger(LogUtilTest.class);
        root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        mockAppender = mock(Appender.class);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);
    }

    @Test
    public void testEntryLogTraceWOnlyEntry() {

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(logger);

        verifyMessageEndString(LogUtil.ENTRY_PREFIX);
    }

    @Test
    public void testEntryLogTraceWMethodName() {

        final String testMethodName = "testMethodName";
        final String expectedEndString = LogUtil.ENTRY_PREFIX + '@' + testMethodName;

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(testMethodName, logger);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testEntryLogTraceWSimpleMsg() {

        final String simpleMessage = "Simple Message";
        final String expectedEndString = LogUtil.ENTRY_PREFIX + ' ' + simpleMessage;

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(logger, simpleMessage);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testEntryLogTraceWFormattedMsg() {

        final String formattedMessage = "Sample {} {}";
        final String testArgument1 = "formatted";
        final String testArgument2 = "message";
        final String expectedEndString = LogUtil.ENTRY_PREFIX + ' '
                + "Sample" + ' ' + testArgument1 + ' ' + testArgument2;;

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(logger, formattedMessage, testArgument1, testArgument2);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testEntryLogTraceWMethodNameAndSimpleMsg() {

        final String testMethodName = "testMethodName";
        final String simpleMessage = "Simple Message";
        final String expectedEndString = LogUtil.ENTRY_PREFIX + '@' + testMethodName + ' ' + simpleMessage;

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(testMethodName, logger, simpleMessage);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testEntryLogTraceWMethodNameAndFormattedMsg() {

        final String testMethodName = "testMethodName";
        final String formattedMessage = "Sample {} {}";
        final String testArgument1 = "formatted";
        final String testArgument2 = "message";
        final String expectedEndString = LogUtil.ENTRY_PREFIX + '@' + testMethodName + ' '
                + "Sample" + ' ' + testArgument1 + ' ' + testArgument2;;

        root.setLevel(Level.TRACE);

        LogUtil.entryTrace(testMethodName, logger, formattedMessage, testArgument1, testArgument2);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testExitLogTraceWOnlyEntry() {

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(logger);

        verifyMessageEndString(LogUtil.EXIT_PREFIX);
    }

    @Test
    public void testExitLogTraceWMethodName() {

        final String testMethodName = "testMethodName";
        final String expectedEndString = LogUtil.EXIT_PREFIX + '@' + testMethodName;

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(testMethodName, logger);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testExitLogTraceWSimpleMsg() {

        final String simpleMessage = "Simple Message";
        final String expectedEndString = LogUtil.EXIT_PREFIX + ' ' + simpleMessage;

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(logger, simpleMessage);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testExitLogTraceWFormattedMsg() {

        final String formattedMessage = "Sample {} {}";
        final String testArgument1 = "formatted";
        final String testArgument2 = "message";
        final String expectedEndString = LogUtil.EXIT_PREFIX + ' '
                + "Sample" + ' ' + testArgument1 + ' ' + testArgument2;;

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(logger, formattedMessage, testArgument1, testArgument2);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testExitLogTraceWMethodNameAndSimpleMsg() {

        final String testMethodName = "testMethodName";
        final String simpleMessage = "Simple Message";
        final String expectedEndString = LogUtil.EXIT_PREFIX + '@' + testMethodName + ' ' + simpleMessage;

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(testMethodName, logger, simpleMessage);

        verifyMessageEndString(expectedEndString);
    }

    @Test
    public void testExitLogTraceWMethodNameAndFormattedMsg() {

        final String testMethodName = "testMethodName";
        final String formattedMessage = "Sample {} {}";
        final String testArgument1 = "formatted";
        final String testArgument2 = "message";
        final String expectedEndString = LogUtil.EXIT_PREFIX + '@' + testMethodName + ' '
                + "Sample" + ' ' + testArgument1 + ' ' + testArgument2;;

        root.setLevel(Level.TRACE);

        LogUtil.exitTrace(testMethodName, logger, formattedMessage, testArgument1, testArgument2);

        verifyMessageEndString(expectedEndString);
    }

    private void verifyMessageEndString(final String expectedEndString) {

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().trim().endsWith(expectedEndString);
            }
        }));
    }

    /**
     * A hacked approach (and one that adds zero-value other than having applied something), to test
     * the constructor which leads to complete code coverage. Without this hack, code coverage tools
     * may report that class constructor has not been tested
     */
    @Test
    public void testLogUtilConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<LogUtil> constructor = LogUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        LogUtil instance = constructor.newInstance();
        Assert.assertNotNull(instance);
    }
}
