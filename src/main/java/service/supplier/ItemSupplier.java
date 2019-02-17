package service.supplier;

import java.util.List;

public interface ItemSupplier<T> {
    List<T> getItems();
}
