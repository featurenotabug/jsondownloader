package test.cases.writer;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.output.Writer;
import test.config.TestAppConfig;
import test.subjects.JsonPlaceholderSamplePosts;
import test.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
abstract public class AbstractWriterTests {

    @Autowired
    private FileUtils fileUtils;

    protected abstract Writer getWriter();
    protected abstract List<?> getItemsToWrite();

    @Test
    public void verifyItemsFromListWrittenToFileCount(){
        fileUtils.cleanDefaultOutputDirectory();
        getWriter().writeItems(getItemsToWrite());
        List<File> outputFiles = fileUtils.getFilesFromDefaultOutputDirectory();

        Assert.assertEquals(getItemsToWrite().size(), outputFiles.size());
    }

    @Test
    public void verifyItemsFromListWrittenToFileContent(){
        fileUtils.cleanDefaultOutputDirectory();
        getWriter().writeItems(getItemsToWrite());
        List<String> outputFilesContent = getFileContentsFromDefaultDirectory();

        Assertions.assertThat(mapItemsToStrings(getItemsToWrite())).containsExactlyInAnyOrder(outputFilesContent.toArray(new String[outputFilesContent.size()]));
    }

    @Test(expected  = IllegalArgumentException.class)
    public void verifyNullItemSourceResultsInError(){
        getWriter().writeItems(null);
    }

    @Test
    public void verifyEmptyItemSourceResultsInNoOutput(){
        fileUtils.cleanDefaultOutputDirectory();
        getWriter().writeItems(List.of());
        List<File> outputFiles = fileUtils.getFilesFromDefaultOutputDirectory();

        Assert.assertEquals(0, outputFiles.size());
    }

    protected abstract List<String> mapItemsToStrings(List<?> itemsList);

    protected List<String> getFileContentsFromDefaultDirectory(){
        return fileUtils.getFilesFromDefaultOutputDirectory().stream().map(fileUtils::getFileContent).collect(Collectors.toList());
    }
}
