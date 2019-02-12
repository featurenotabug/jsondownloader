package service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import model.JsonDTO;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper<T extends JsonDTO> implements Mapper<T> {

    private final ObjectMapper mapper;
    private final CollectionType collectionType;

    public JacksonMapper(Class<T> dtoType) {
        this.mapper = new ObjectMapper();
        this.collectionType = constructCollectionType(dtoType);
    }

    private CollectionType constructCollectionType(Class<T> dtoType){
        return mapper.getTypeFactory().constructCollectionType(List.class, dtoType);
    }

    @Override
    public List<T> fromString(String content) {
        try {
            return jsonStringToObjects(content);
        } catch (IOException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    private List<T> jsonStringToObjects(String content) throws IOException {
        return mapper.readValue(content, collectionType);
    }

    private UncheckedIOException uncheckedExceptionOf(IOException e) {
        return new UncheckedIOException(e);
    }

    @Override
    public String stringFrom(T item) {
        try {
            return objectToJsonString(item);
        } catch (JsonProcessingException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    private String objectToJsonString(T item) throws JsonProcessingException {
        return mapper.writeValueAsString(item);
    }
}
