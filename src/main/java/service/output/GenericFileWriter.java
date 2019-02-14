package service.output;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public final class GenericFileWriter extends AbstractFileWriter<Object> {

    public GenericFileWriter(@NotNull String directoryPath) {
        super(directoryPath);
    }

    @Override
    protected List<String> itemsToStrings(@NotNull List<?> items) {
        return items.stream().map(String::valueOf).collect(Collectors.toList());
    }


}

