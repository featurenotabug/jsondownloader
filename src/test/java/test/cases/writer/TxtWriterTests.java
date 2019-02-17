package test.cases.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.output.Writer;
import test.subjects.JsonPlaceholderSamplePosts;
import test.utils.FileUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TxtWriterTests extends AbstractWriterTests {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    @Qualifier("genericFileWriter")
    private Writer txtWriter;

    private final List<Object> mixedTypesList = List.of(1, 2.0, "3", JsonPlaceholderSamplePosts.getFirstPost());

    @Override
    protected Writer getWriter() {
        return txtWriter;
    }

    @Override
    protected List<?> getItemsToWrite() {
        return mixedTypesList;
    }

    @Override
    protected List<String> stringsOfItemsToWrite() {
        return mixedTypesList.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
