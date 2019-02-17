package test.cases.downloader;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.downloader.Downloader;
import test.config.TestAppConfig;
import test.utils.FileUtils;
import test.utils.SystemOutputInterceptor;

import java.io.*;
import java.util.List;
import static java.util.function.Predicate.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class DownloaderTests {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    @Qualifier("httpPostsToJsonFileDownloader")
    private Downloader jsonPostsDownloader;

    @Autowired
    @Qualifier("httpPostsToConsoleDownloader")
    private Downloader consolePostsDownloader;

    @Autowired
    @Qualifier("genericHttpPostsDownloader")
    private Downloader txtPostsDownloader;


    @Test
    public void verify100JsonFilesWritten(){
        fileUtils.cleanDefaultOutputDirectory();
        jsonPostsDownloader.download();
        List<File> outputFiles = fileUtils.getFilesFromDefaultOutputDirectory();
        long jsonFileCount = outputFiles.stream().filter(this::hasJsonExtension).count();
        long expectedFileCount = 100;

        String errorMessage = "Expected to find " + expectedFileCount + " files with .json extension, instead found:" + jsonFileCount;
        Assert.assertEquals(errorMessage, expectedFileCount, jsonFileCount);
    }

    @Test
    public void verify100TxtFilesWritten(){
        fileUtils.cleanDefaultOutputDirectory();
        txtPostsDownloader.download();
        List<File> outputFiles = fileUtils.getFilesFromDefaultOutputDirectory();
        long txtFileCount = outputFiles.stream().filter(this::hasTxtExtension).count();
        long expectedFileCount = 100;

        String errorMessage = "Expected to find " + expectedFileCount + " files with .txt extension, instead found:" + txtFileCount;
        Assert.assertEquals(errorMessage, expectedFileCount, txtFileCount);
    }

    @Test
    public void verify100ItemsSentToConsole() {
        String consoleOutput = SystemOutputInterceptor.captureConsoleOutAction(() -> consolePostsDownloader.download());
        System.out.println(consoleOutput);
        List<String> consoleMessages = List.of(consoleOutput.split("model.PostDTO@"));

        long consoleMessageCount = consoleMessages.stream().filter(not(String::isBlank)).count();
        long expectedMessageCount = 100;

        String errorMessage = "Expected to find " + expectedMessageCount + " messages sent to console, instead found:" + consoleMessageCount;
        Assert.assertEquals(errorMessage, expectedMessageCount, consoleMessageCount);
    }

    private boolean hasJsonExtension(@NotNull File file){
        return fileUtils.fileHasExtension(file, "json");
    }

    private boolean hasTxtExtension(@NotNull File file){
        return fileUtils.fileHasExtension(file, "txt");
    }
}
