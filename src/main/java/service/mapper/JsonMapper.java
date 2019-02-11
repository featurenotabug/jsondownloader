package service.mapper;

import model.DTO;

import java.util.List;

public class JsonMapper<T extends DTO> implements Mapper<T> {
    @Override
    public List<T> mapToObjects(String text) {
        return null;
        //TODO
    }
}
