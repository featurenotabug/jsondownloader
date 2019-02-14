package service.output;

import org.jetbrains.annotations.NotNull;
import service.logger.ConsoleLogger;
import service.logger.Logger;

import java.util.List;

public final class ConsoleWriter implements Writer<Object> {

    private Logger logger;

    public ConsoleWriter(){
        this.logger = new ConsoleLogger();
    }

    @Override
    public void writeItems(@NotNull List<?> items) {
        items.forEach(item -> logger.out(String.valueOf(item)));
    }
}
