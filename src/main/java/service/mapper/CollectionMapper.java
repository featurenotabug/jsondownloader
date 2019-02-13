package service.mapper;

import java.util.List;
//extended Mapper interface, for types that require different logic for parsing collections that single objects
public interface CollectionMapper<T> extends Mapper<T> {
    List<T> mapToListFromString(String items);
    String mapToStringFromList(List<T> items);
}
