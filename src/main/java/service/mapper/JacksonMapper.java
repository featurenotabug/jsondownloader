package service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import model.JsonDTO;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper<T extends JsonDTO> implements Mapper<T> {

    private final ObjectMapper mapper;
    private final JavaType type;
    private final CollectionType collectionType;


    public JacksonMapper(Class<T> dtoType) {
        this.mapper = new ObjectMapper();
        this.type = constructType(dtoType);
        this.collectionType = constructCollectionType(dtoType);
    }

    private CollectionType constructCollectionType(Class<T> dtoType){
        return mapper.getTypeFactory().constructCollectionType(List.class, dtoType);
    }

    private JavaType constructType(Class<T> dtoType){
        return mapper.getTypeFactory().constructType(dtoType);
    }

    @Override
    public T objectFromString(String content) {
        return (T) readValue(content, type);

    }

    @Override
    public List<T> objectsFromString(String content) {
        return (List<T>) readValue(content, collectionType);
    }

    private Object readValue(String content, JavaType type){
        try {
            return mapper.readValue(content, type);
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
    public String stringFromObject(T item) {
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
