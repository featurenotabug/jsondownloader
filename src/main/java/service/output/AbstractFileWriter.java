package service.output;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import service.logger.ConsoleLogger;
import service.logger.Logger;
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

public abstract class AbstractFileWriter<T> implements Writer<T> {
    protected String separator;
    protected String baseFileName;
    protected String fileExtension;
    protected String timestampFormat;

    private final String directoryPath;
    private final AtomicInteger count;
    private final DateFormat timestampFormatter;

    private final Logger logger;

    public AbstractFileWriter(@NotNull String directoryPath) {
        this.separator = "_";
        this.baseFileName = "item";
        this.fileExtension = ".txt";
        this.timestampFormat = "yyyyMMddHHmmssSSS"; //e.g. 20190212121913212

        this.directoryPath = directoryPath;
        this.count = new AtomicInteger();
        this.timestampFormatter = new SimpleDateFormat(timestampFormat);
        this.logger = new ConsoleLogger();

    }

    abstract protected List<String> itemsToStrings(@NotNull List<? extends T> items);

    @Override
    public void writeItems(@NotNull List<? extends T> items) {
        verifyOutputDirectory();
        itemsToStrings(items).forEach(this::tryWriteToFile);
        log("All data has been written to files.");
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
        log("Writing file:" + path.toString());
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
        log("Checking if output directory exists:" + outputDir.getAbsolutePath());
        if (!outputDir.exists()) {
            outputDir.mkdir();
            log("Creating output directory");
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
                .append(separator)
                .append(baseFileName)
                .append(separator)
                .append(count.incrementAndGet())
                .append(fileExtension);
        return pathBuilder.toString();
    }

    private void log(String message){
        logger.log(message);
    }
}
