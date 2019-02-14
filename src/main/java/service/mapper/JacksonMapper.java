package service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import model.JsonDTO;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonMapper<T extends JsonDTO> implements CollectionMapper<T> {

    private final ObjectMapper mapper;
    private final JavaType type;
    private final CollectionType collectionType;
    private final boolean prettyPrint;

    public JacksonMapper(Class<T> dtoType, boolean prettyPrint) {
        this.mapper = new ObjectMapper();
        this.type = constructType(dtoType);
        this.collectionType = constructCollectionType(dtoType);
        this.prettyPrint = prettyPrint;
    }

    public JacksonMapper(Class<T> dtoType) {
        this(dtoType, false);
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
    public T mapFromString(String item) {
        return (T) jsonStringToObject(item, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    //value returned by ObjectMapper when type argument is constructed from Class<T> and List.class, will be of type List<T>.
    public List<T> mapToListFromString(String items) {
        return (List<T>) jsonStringToObject(items, collectionType);
    }

    private Object jsonStringToObject(String content, JavaType objectType){
        try {
            return mapper.readValue(content, objectType);
        } catch (IOException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    @Override
    public String mapToString(T item) {
        return objectToJsonString(item);
    }

    @Override
    public String mapToStringFromList(List<T> items) {
        return objectToJsonString(items);
    }

    private String objectToJsonString(Object item) {
        try {
            return writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    private String writeValueAsString(Object item) throws JsonProcessingException {
        return prettyPrint ? writeValueAsStringWithFormatting(item) : writeValueAsStringWithoutFormatting(item);
    }

    private String writeValueAsStringWithoutFormatting(Object item) throws JsonProcessingException {
        return mapper.writeValueAsString(item);
    }

    private String writeValueAsStringWithFormatting(Object item) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
    }

    private UncheckedIOException uncheckedExceptionOf(IOException e) {
        return new UncheckedIOException(e);
    }
}
