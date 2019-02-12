package service.output;

import model.JsonDTO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import service.logger.ConsoleLogger;
import service.logger.Logger;
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

    private final Logger logger;

    public JsonFileWriter(@NotNull String directoryPath,  @NotNull Mapper<T> jsonMapper) {
        this.directoryPath = directoryPath;
        this.jsonMapper = jsonMapper;
        this.count = new AtomicInteger();
        this.timestampFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
        this.logger = new ConsoleLogger();
    }

    @Override
    public void writeItems(@NotNull List<T> items) {
        verifyOutputDirectory();
        itemsToStrings(items).forEach(this::tryWriteToFile);
        logger.log("All data has been written to files.");
    }

    private List<String> itemsToStrings(List<T> items){
        return jsonMapper.stringsFromObjects(items);
    }

    private void tryWriteToFile(String item) {
        try {
            writeToFile(item);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeToFile(String item) throws IOException {
        Path path = getPathToWriteFile();
        logger.log("Writing file:" + path.toString());
        Files.writeString(path, item);
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
        logger.log("Checking if output directory exists:" + outputDir.getAbsolutePath());
        if (!outputDir.exists()) {
            outputDir.mkdir();
            logger.log("Creating output directory");
        }
    }

    private Path getPathToWriteFile(){
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
