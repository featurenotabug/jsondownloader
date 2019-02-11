package service.mapper;

import java.util.List;

public interface Mapper<T> {
    List<T> mapToObjects(String text);
}
