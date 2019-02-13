import configuration.DefaultAppConfig;
import model.PostDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.mapper.Mapper;
import service.dataprovider.ItemProvider;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultAppConfig.class)
public class JacksonMapperTests {

    @Autowired
    @Qualifier("postsMapper")
    Mapper<PostDTO> jacksonMapper;

    @Autowired
    @Qualifier("httpPostsProvider")
    ItemProvider jsonPostsProvider;

    List items;

    @Before
    public void loadItems(){
        items =  jsonPostsProvider.getItems();
    }

    @Test
    public void verifySinglePostJsonSerializationAndDeserialization(){
        int expectedUserId = 1;
        int expectedId = 1;
        String expectedTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String expectedBody = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";

        PostDTO post = new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);
        String jsonPost  = jacksonMapper.serialize(post);
        PostDTO deserializedPost = jacksonMapper.deserialize(jsonPost);

        Assert.assertEquals(post, deserializedPost);

    }

    @Test
    public void verifyAllDownloadedPostsJsonSerializationAndDeserialization(){
        List<String> serializedPosts = jacksonMapper.serialize(items);
        List<PostDTO> deserializedPosts = jacksonMapper.deserialize(serializedPosts);

        Assert.assertEquals(items, deserializedPosts);

    }

}
