package service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T> {
    List<T> fromString(String content);
    String stringFrom(T item); //not named simply toString to avoid ambiguity with Object class method


    default List<String> stringsFrom(List<T> items){
        return items.stream().map(this::stringFrom).collect(Collectors.toList());
    }

    default List<String> stringsFrom(String content){
        return stringsFrom(fromString(content));
    }
}
