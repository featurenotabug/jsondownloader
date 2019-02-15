package test.utils;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@PropertySource("classpath:application.properties")
@Component
public class FileUtils {

    @Value("${output.dir}")
    private String outputDirectoryPath;

    public FileUtils(){
    }

    public FileUtils(String outputDirectoryPath){
        this.outputDirectoryPath = outputDirectoryPath;
    }

    public List<File> getFilesFromDirectory(@NotNull String directoryPath){
        File directory = new File(directoryPath);
        errorIfDirectoryNotExists(directory);
        return List.of(getFileArrayFromDir(directory));
    }

    public List<File> getFilesFromDefaultOutputDirectory(){
        return getFilesFromDirectory(outputDirectoryPath);
    }

    public void cleanDirectory(@NotNull String directoryPath){
        File directory = new File(directoryPath);
        if(directoryExists(directory))
            deleteFilesFromDirectory(directory);
    }

    public void cleanDefaultOutputDirectory(){
        cleanDirectory(outputDirectoryPath);
    }

    public String getFileContent(File file){
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw uncheckedExceptionOf(e);
        }
    }

    public boolean fileHasExtension(@NotNull File file, String extension){ //pass extension without dot
        return FilenameUtils.isExtension(file.getName(), extension);
    }

    private void deleteFilesFromDirectory(File directory){
        for(File file: verifyFiles(directory.listFiles(), directory)){
            if (!file.isDirectory()) file.delete();
        }
    }

    private File[] getFileArrayFromDir(File directory){
        return verifyFiles(directory.listFiles(), directory);
    }

    private boolean directoryExists(File directory){
        return directory.exists();
    }

    private void errorIfDirectoryNotExists(File directory){
        if(!directoryExists(directory))
            throw dirNotFoundException(directory);
    }

    private File[] verifyFiles(File[] files, File directory){
        return Objects.requireNonNull(files, "An error occurred while getting files from directory:" + directory.getAbsolutePath());
    }

    private RuntimeException dirNotFoundException(File directory){
        return new UncheckedIOException(new FileNotFoundException("Following directory does not exist:" + directory.getAbsolutePath()));
    }

    private RuntimeException uncheckedExceptionOf(IOException e){
        return new UncheckedIOException(e);
    }

}
