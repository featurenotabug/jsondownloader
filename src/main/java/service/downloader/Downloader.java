package service.downloader;

import java.util.List;

public interface Downloader<T> {
    List<T> getItems();
}
