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
    @SuppressWarnings("unchecked")
    //value returned by ObjectMapper when type argument is constructed from Class<T>, will be of type T.
    public T deserialize(String content) {
        return (T) jsonStringToObject(content, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    //value returned by ObjectMapper when type argument is constructed from Class<T> and List.class, will be of type List<T>.
    public List<T> deserializeCollection(String content) {
        return (List<T>) jsonStringToObject(content, collectionType);
    }

    private Object jsonStringToObject(String content, JavaType objectType){
        try {
            return mapper.readValue(content, objectType);
        } catch (IOException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    @Override
    public String serialize(T item) {
        try {
            return objectToJsonString(item);
        } catch (JsonProcessingException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    private String objectToJsonString(T item) throws JsonProcessingException {
        return mapper.writeValueAsString(item);
    }

    private UncheckedIOException uncheckedExceptionOf(IOException e) {
        return new UncheckedIOException(e);
    }
}
