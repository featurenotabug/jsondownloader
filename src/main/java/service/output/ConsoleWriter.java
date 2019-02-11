package service.output;

import java.util.List;

public class ConsoleWriter implements Writer {

    @Override
    public void writeItems(List<?> items) {
        items.forEach(System.out::println);
    }
}
