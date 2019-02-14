package service.downloader;

import org.jetbrains.annotations.NotNull;
import service.dataprovider.ItemSupplier;
import service.output.Writer;

public final class DefaultDownloader<T> implements Downloader {

    private final ItemSupplier<T> itemSupplier;
    private final Writer<? super T> outputWriter;

    public DefaultDownloader(@NotNull ItemSupplier<T> itemSupplier, @NotNull Writer<? super T> outputWriter) {
        this.itemSupplier = itemSupplier;
        this.outputWriter = outputWriter;
    }

    @Override
    public void download() {
        outputWriter.writeItems(itemSupplier.getItems());
    }
}
