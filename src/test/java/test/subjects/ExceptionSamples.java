package test.subjects;

import service.logger.Logger;

public class ExceptionSamples {

    public static final Exception SAMPLE_EXCEPTION = new Exception("SAMPLE EXCEPTION");

    public static final String SAMPLE_STACKTRACE = Logger.exceptionStackTraceToString(SAMPLE_EXCEPTION);
}
