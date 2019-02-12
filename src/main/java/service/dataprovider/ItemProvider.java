package service.dataprovider;

import java.util.List;

public interface ItemProvider<T> {
    List<T> getItems();
}
