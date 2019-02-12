import configuration.DefaultAppConfig;
import model.JsonDTO;
import model.PostDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.provider.ItemProvider;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultAppConfig.class)
public class HttpJsonPostProviderTests {

    @Autowired
    @Qualifier("httpPostsProvider")
    ItemProvider jsonPostsProvider;

    List<JsonDTO> items;

    @Before
    public void loadItems(){
        items =  jsonPostsProvider.getItems();
    }

    @Test
    public void verify100PostsDownloaded(){
        String errorMessage = "Expected to download 100 items, instead found:" + items.size();
        Assert.assertEquals(errorMessage,100, items.size());
    }

    @Test
    public void verifyFirstPostContent(){
        String errorMessage = "Expected content of first post does not match actually downloaded first post.";

        int expectedUserId = 1;
        int expectedId = 1;
        String expectedTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String expectedBody = "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto";

        JsonDTO downloadedFirstPost = items.get(0);
        PostDTO expectedFirstPost = new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);

        Assert.assertEquals(errorMessage, expectedFirstPost, downloadedFirstPost);
    }
}
