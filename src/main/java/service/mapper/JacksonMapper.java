package service.mapper;

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
            return stringContentToObjects(content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<T> stringContentToObjects(String content) throws IOException {
        return mapper.readValue(content, collectionType);
    }

}
