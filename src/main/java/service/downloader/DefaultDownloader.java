package service.downloader;

import service.dataprovider.ItemProvider;
import service.output.Writer;

public class DefaultDownloader<T> implements Downloader {

    private final ItemProvider<T> itemProvider;
    private final Writer outputWriter;

    public DefaultDownloader(ItemProvider<T> itemProvider, Writer outputWriter) {
        this.itemProvider = itemProvider;
        this.outputWriter = outputWriter;
    }

    @Override
    public void download() {
        outputWriter.writeItems(itemProvider.getItems());
    }
}
