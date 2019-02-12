package service.output;

import model.JsonDTO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import service.mapper.Mapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonFileWriter<T extends JsonDTO> implements Writer<T> {

    private static final String SEPARATOR = "_";
    private static final String BASE_FILE_NAME = "item";
    private static final String FILE_EXTENSION = ".json";

    private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS"; //e.g. 20190212121913212

    private final String directoryPath;
    private final AtomicInteger count;
    private final DateFormat timestampFormatter;
    private final Mapper<T> jsonMapper;

    public JsonFileWriter(@NotNull String directoryPath,  @NotNull Mapper<T> jsonMapper) {
        this.directoryPath = directoryPath;
        this.jsonMapper = jsonMapper;
        this.count = new AtomicInteger();
        this.timestampFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
    }

    @Override
    public void writeItems(@NotNull List<T> items) {
        itemsToStrings(items).forEach(this::tryWriteToFile);
    }

    private List<String> itemsToStrings(List<T> items){
        return jsonMapper.stringsFrom(items);
    }

    private void tryWriteToFile(String item) {
        try {
            writeToFile(item);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeToFile(String item) throws IOException {
        verifyOutputDirectory();
        Files.writeString(getPath(), item);
    }

    private void verifyOutputDirectory(){
        createOutputDirectoryIfNotExists(getOutputDirectory());
    }

    @NotNull
    @Contract(" -> new")
    private File getOutputDirectory(){
        return new File(directoryPath);
    }

    private void createOutputDirectoryIfNotExists(@NotNull File outputDir){
        if (!outputDir.exists()) outputDir.mkdir();
    }

    private Path getPath(){
        return Paths.get(constructFilePath());
    }

    @NotNull
    private String constructFilePath(){
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(directoryPath)
                .append(timestampFormatter.format(new Date()))
                .append(SEPARATOR)
                .append(BASE_FILE_NAME)
                .append(SEPARATOR)
                .append(count.incrementAndGet())
                .append(FILE_EXTENSION);
        return pathBuilder.toString();
    }
}
