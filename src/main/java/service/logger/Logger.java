package service.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface Logger {
    void out(String message);
    void error(String message);

    default void error(Exception e){
        error(exceptionStackTraceToString(e));
    }

    private String exceptionStackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}