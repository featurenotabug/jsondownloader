package service.output;

import model.JsonDTO;
import org.jetbrains.annotations.NotNull;
import service.mapper.Mapper;

import java.util.List;

public class JsonFileWriter<T extends JsonDTO> extends AbstractFileWriter<T> {

    private final Mapper<T> jsonMapper;

    public JsonFileWriter(@NotNull String directoryPath, @NotNull Mapper<T> jsonMapper) {
        super(directoryPath);
        this.jsonMapper = jsonMapper;
        this.fileExtension = ".json";
    }

    @Override
    protected List<String> itemsToStrings(@NotNull List<? extends T> items){
        return jsonMapper.stringsFromObjects(items);
    }
}
