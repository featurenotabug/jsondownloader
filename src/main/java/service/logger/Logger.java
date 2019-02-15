package service.logger;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface Logger {
    void out(String message);
    void error(String message);

    default void error(Exception e){
        error(exceptionStackTraceToString(e));
    }

    static String exceptionStackTraceToString(@NotNull Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}