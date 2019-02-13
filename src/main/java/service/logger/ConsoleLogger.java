package service.logger;

public class ConsoleLogger implements Logger {
    @Override
    public void out(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message) {
        System.err.println(message);
    }
}
