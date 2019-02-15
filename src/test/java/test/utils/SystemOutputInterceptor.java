package test.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SystemOutputInterceptor {

    private SystemOutputInterceptor(){}

    private ByteArrayOutputStream temporaryOutputStream;
    private PrintStream temporarySystemStream;
    private PrintStream originalSystemStream;

    public static String captureConsoleOutAction(ConsoleWritingAction action){
        return new SystemOutputInterceptor().interceptConsoleOutActionResult(action);
    }

    public static String captureConsoleErrorAction(ConsoleWritingAction action){
        return new SystemOutputInterceptor().interceptConsoleErrorActionResult(action);
    }

    private String interceptConsoleOutActionResult(ConsoleWritingAction action){
        startCapturingOut();
        executeAction(action);
        stopCapturingOut();
        return getCapturedValue().trim();
    }

    private String interceptConsoleErrorActionResult(ConsoleWritingAction action){
        startCapturingError();
        executeAction(action);
        stopCapturingError();
        return getCapturedValue().trim();
    }

    private void executeAction(ConsoleWritingAction action){
        action.execute();
    }

    private void startCapturingOut(){
        setupTemporaryStreams();
        originalSystemStream = System.out;
        System.setOut(temporarySystemStream);
    }

    private void startCapturingError(){
        setupTemporaryStreams();
        originalSystemStream = System.err;
        System.setErr(temporarySystemStream);
    }

    private void setupTemporaryStreams(){
        temporaryOutputStream = new ByteArrayOutputStream();
        temporarySystemStream = new PrintStream(temporaryOutputStream);
    }

    private void stopCapturingOut(){
        System.out.flush();
        System.setOut(originalSystemStream);
    }

    private void stopCapturingError(){
        System.err.flush();
        System.setErr(originalSystemStream);
    }

    private String getCapturedValue() {
        return temporaryOutputStream.toString();
    }

    @FunctionalInterface
    public interface ConsoleWritingAction {
        void execute();
    }


}
