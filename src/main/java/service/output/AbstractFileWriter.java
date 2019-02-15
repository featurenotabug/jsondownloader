package service.output;

import org.jetbrains.annotations.NotNull;
import service.logger.ConsoleLogger;
import service.logger.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class AbstractFileWriter<T> implements Writer<T> {
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
        tryWritingToFiles(items);
        logInfo("All data has been written to files.");
    }
    private void verifyOutputDirectory(){
        createOutputDirectoryIfNotExists(getOutputDirectory());
    }

    private File getOutputDirectory(){
        return new File(directoryPath);
    }

    private void createOutputDirectoryIfNotExists(@NotNull File outputDir){
        logInfo("Checking if output directory exists:" + outputDir.getAbsolutePath());
        if (!directoryExists(outputDir)) {
            createDirectory(outputDir);
            logInfo("Creating output directory");
        }
    }

    private boolean directoryExists(File directory){
        return directory.exists();
    }

    private void createDirectory(File directory){
        directory.mkdir();
    }

    private void tryWritingToFiles(List<? extends T> items){
        itemsToStrings(items).forEach(this::tryWritingToFile);
    }

    private void tryWritingToFile(String item) {
        Path filePath = getPathToWriteFile();
        try {
            writeToFile(item, filePath);
        } catch (IOException e) {
            handleFileWritingException(e, filePath);
        }
    }

    private void writeToFile(String item, Path filePath) throws IOException {
        logInfo("Writing file:" + filePath.toString());
        Files.writeString(filePath, item);
    }

    private void handleFileWritingException(IOException e, Path filePath){
        logError("An input/output error occurred while trying to write file:" + filePath.toString());
        logError(e);
    }

    @NotNull
    private Path getPathToWriteFile(){
        return Paths.get(constructFilePathAndIncrementCount());
    }

    @NotNull
    private String constructFilePathAndIncrementCount(){
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

    protected void logInfo(String message){
        logger.out(message);
    }

    protected void logError(String message){
        logger.error(message);
    }

    protected void logError(Exception e){
        logger.error(e);
    }
}
