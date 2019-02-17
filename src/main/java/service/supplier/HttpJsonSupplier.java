package service.supplier;

import service.http.HTTPConnector;
import model.JsonDTO;
import org.jetbrains.annotations.NotNull;
import service.mapper.CollectionMapper;


import java.util.List;

public final class HttpJsonSupplier<T extends JsonDTO> implements ItemSupplier<T> {
    private final String url;
    private final HTTPConnector httpConnector;
    private final CollectionMapper<T> jsonMapper;

    public HttpJsonSupplier(@NotNull String url, @NotNull HTTPConnector httpConnector, @NotNull CollectionMapper<T> jsonMapper) {
        checkIsUrlEmpty(url);
        this.url = url;
        this.httpConnector = httpConnector;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public List<T> getItems() {
        return extractItemsFromUrl();
    }

    private List<T> extractItemsFromUrl(){
        return extractItemsFromResponse(downloadContentFromUrl());
    }

    private String downloadContentFromUrl(){
        return httpConnector.getResponse(url);
    }

    private List<T> extractItemsFromResponse(String response){
        return jsonMapper.mapToListFromString(response);
    }

    private void checkIsUrlEmpty(String url){
        if(urlIsEmpty(url))
            throw emptyUrlException();
    }

    private boolean urlIsEmpty(String url){
        return url == null || url.trim().isEmpty();
    }

    private RuntimeException emptyUrlException() {
        return new IllegalArgumentException("Data source URL cannot be null or blank.");
    }
}
