package service.provider;

import service.http.HTTPConnector;
import model.JsonDTO;
import org.jetbrains.annotations.NotNull;
import service.mapper.Mapper;


import java.util.List;

public class HttpJsonProvider<T extends JsonDTO> implements ItemProvider<T> {
    private final String url;

    private final HTTPConnector httpConnector;
    private final Mapper<T> jsonMapper;

    public HttpJsonProvider(@NotNull String url, @NotNull HTTPConnector httpConnector, @NotNull Mapper<T> jsonMapper) {
        checkIsUrlEmpty(url);
        this.url = url;
        this.httpConnector = httpConnector;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public List<T> getItems() {
        return jsonMapper.fromString(getContentFromUrl());
    }

    private String getContentFromUrl(){
        return httpConnector.getResponse(url);
    }

    private void checkIsUrlEmpty(String url){
        if(url == null || url.trim().isEmpty())
            throw new IllegalArgumentException("Data source URL cannot be null or blank.");
    }
}
