package test.cases;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class WriterTests {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    @Qualifier("jsonFileWriter")
    private Writer jsonWriter;

    @Autowired
    @Qualifier("genericFileWriter")
    private Writer txtWriter;

    @Autowired
    @Qualifier("consoleWriter")
    private Writer consoleWriter;

    private static final List<Object> mixedTypesList = List.of(1, 2.0, "3", JsonPlaceholderSamplePosts.getFirstPost());

    @Test
    public void verifyAllItemsFromListOfMixedTypesWrittenToTxt(){
        fileUtils.cleanDefaultOutputDirectory();
        txtWriter.writeItems(mixedTypesList);
        List<File> outputFiles = fileUtils.getFilesFromDefaultOutputDirectory();

        Assert.assertEquals(mixedTypesList.size(), outputFiles.size());
    }

    @Test
    public void verifyItemsFromListOfMixedTypesContentWrittenToTxt(){
        fileUtils.cleanDefaultOutputDirectory();
        txtWriter.writeItems(mixedTypesList);
        List<String> outputFilesContent = getFileContentsFromDefaultDirectory();

        Assertions.assertThat(mapItemsToStrings(mixedTypesList)).containsExactlyInAnyOrder(outputFilesContent.toArray(new String[outputFilesContent.size()]));
    }

    private List<String> mapItemsToStrings(List<?> itemsList){
        return itemsList.stream().map(String::valueOf).collect(Collectors.toList());
    }

    private List<String> getFileContentsFromDefaultDirectory(){
        return fileUtils.getFilesFromDefaultOutputDirectory().stream().map(fileUtils::getFileContent).collect(Collectors.toList());
    }
}
