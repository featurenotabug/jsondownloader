package test.cases.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.mapper.CollectionMapper;
import service.output.Writer;

import static test.subjects.JsonPlaceholderSamplePosts.*;

import java.util.List;

public class JsonWriterTests extends AbstractWriterTests {

    @Autowired
    @Qualifier("jsonFileWriter")
    private Writer jsonWriter;

    @Autowired
    @Qualifier("postsMapper")
    private CollectionMapper jacksonMapper;

    private final List<Object> jsonPostsList = List.of(getFirstPost(), get42ndPost(), getLastPost());

    @Override
    protected Writer getWriter() {
        return jsonWriter;
    }

    @Override
    protected List<?> getItemsToWrite() {
        return jsonPostsList;
    }

    @Override
    protected List<String> stringsOfItemsToWrite() {
        return jacksonMapper.mapToStringList(jsonPostsList);
    }
}
