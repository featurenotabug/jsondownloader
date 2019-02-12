package service.downloader;

import org.jetbrains.annotations.NotNull;
import service.dataprovider.ItemProvider;
import service.output.Writer;

public class DefaultDownloader<T> implements Downloader {

    private final ItemProvider<T> itemProvider;
    private final Writer<T> outputWriter;

    public DefaultDownloader(@NotNull ItemProvider<T> itemProvider, @NotNull Writer<T> outputWriter) {
        this.itemProvider = itemProvider;
        this.outputWriter = outputWriter;
    }

    @Override
    public void download() {
        outputWriter.writeItems(itemProvider.getItems());
    }
}
