package service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T> {
    String serialize(T item);
    T deserialize(String content);
    List<T> deserializeCollection(String content);

    default List<String> serialize(List<? extends T> items){
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    default List<T> deserialize(List<String> items){
        return items.stream().map(this::deserialize).collect(Collectors.toList());
    }
}
