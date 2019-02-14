package service.dataprovider;

import java.util.List;

public interface ItemSupplier<T> {
    List<T> getItems();
}
