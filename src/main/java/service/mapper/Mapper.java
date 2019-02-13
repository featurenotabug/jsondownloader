package service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T> {
    String mapToString(T item);
    T mapFromString(String item);

    default List<String> mapToStringList(List<? extends T> items){
        return items.stream().map(this::mapToString).collect(Collectors.toList());
    }

    default List<T> mapFromStringList(List<String> items){
        return items.stream().map(this::mapFromString).collect(Collectors.toList());
    }
}
