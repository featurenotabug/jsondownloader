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
import service.dataprovider.ItemProvider;

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
        String expectedBody = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";

        JsonDTO downloaded = items.get(0);
        PostDTO expected = new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);

        Assert.assertEquals(errorMessage, expected, downloaded);
    }

    @Test
    public void verifyLastPostContent(){
        String errorMessage = "Expected content of last post does not match actually downloaded last post.";

        int expectedUserId = 10;
        int expectedId = 100;
        String expectedTitle = "at nam consequatur ea labore ea harum";
        String expectedBody = "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut";

        JsonDTO downloaded = items.get(items.size() - 1);
        PostDTO expected = new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);

        Assert.assertEquals(errorMessage, expected, downloaded);
    }

    @Test
    public void verify42ndPostContent(){
        String errorMessage = "Expected content of last post does not match actually downloaded last post.";

        int expectedUserId = 5;
        int expectedId = 42;
        String expectedTitle = "commodi ullam sint et excepturi error explicabo praesentium voluptas";
        String expectedBody = "odio fugit voluptatum ducimus earum autem est incidunt voluptatem\nodit reiciendis aliquam sunt sequi nulla dolorem\nnon facere repellendus voluptates quia\nratione harum vitae ut";

        JsonDTO downloaded = items.get(42 - 1);
        PostDTO expected = new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);

        Assert.assertEquals(errorMessage, expected, downloaded);
    }

}
