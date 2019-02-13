package service.output;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Writer<T> {
    void writeItems(@NotNull List<? extends T> items);
}
