package service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T> {
    String stringFromObject(T item);
    T objectFromString(String content);
    List<T> objectsFromString(String content);

    default List<String> stringsFromObjects(List<T> items){
        return items.stream().map(this::stringFromObject).collect(Collectors.toList());
    }

    default List<T> objectsFromStrings(List<String> items){
        return items.stream().map(this::objectFromString).collect(Collectors.toList());
    }
}
