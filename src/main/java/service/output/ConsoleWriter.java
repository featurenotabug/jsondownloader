package service.output;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConsoleWriter<T> implements Writer<T> {

    @Override
    public void writeItems(@NotNull List<T> items) {
        items.forEach(System.out::println);
    }
}
