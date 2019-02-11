package service.downloader;

import service.http.HTTPConnector;
import model.DTO;
import org.jetbrains.annotations.NotNull;
import service.mapper.Mapper;

import java.util.List;

public class JsonDownloader<T extends DTO> implements Downloader<T> {
    private final String url;
    private final HTTPConnector httpConnector;
    private final Mapper mapper;

    public JsonDownloader(@NotNull String url, @NotNull HTTPConnector httpConnector, @NotNull Mapper mapper) {
        checkIsUrlEmpty(url);
        this.url = url;
        this.httpConnector = httpConnector;
        this.mapper = mapper;
    }

    @Override
    public List<T> getItems() {
        String content = httpConnector.get(url);
        System.out.println(content);
        //TODO
        return null;
    }

    private void checkIsUrlEmpty(String url){
        if(url == null || url.trim().isEmpty())
            throw new IllegalArgumentException("Data source URL cannot be null or blank.");
    }
}
