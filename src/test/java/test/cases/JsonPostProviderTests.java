package test.cases;

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
import service.dataprovider.ItemSupplier;
import test.config.TestAppConfig;
import test.subjects.JsonPlaceholderSamplePosts;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class JsonPostProviderTests {

    @Autowired
    @Qualifier("httpPostsProvider")
    private ItemSupplier<? extends JsonDTO> jsonPostsProvider;

    private static List<? extends JsonDTO> items;

    @Before
    public void initialize(){
        if(items == null)
            items = jsonPostsProvider.getItems();
    }


    @Test
    public void verify100PostsDownloaded(){
        String errorMessage = "Expected to download 100 items, instead found:" + items.size();
        Assert.assertEquals(errorMessage,100, items.size());
    }

    @Test
    public void verifyFirstPostContent(){
        String errorMessage = "Expected content of first post does not match actually downloaded first post.";
        JsonDTO downloaded = items.get(0);
        PostDTO expected = JsonPlaceholderSamplePosts.getFirstPost();
        Assert.assertEquals(errorMessage, expected, downloaded);
    }

    @Test
    public void verifyLastPostContent(){
        String errorMessage = "Expected content of last post does not match actually downloaded last post.";
        JsonDTO downloaded = items.get(items.size() - 1);
        PostDTO expected = JsonPlaceholderSamplePosts.getLastPost();
        Assert.assertEquals(errorMessage, expected, downloaded);
    }

    @Test
    public void verify42ndPostContent(){
        String errorMessage = "Expected content of last post does not match actually downloaded last post.";
        JsonDTO downloaded = items.get(42 - 1);
        PostDTO expected = JsonPlaceholderSamplePosts.get42ndPost();
        Assert.assertEquals(errorMessage, expected, downloaded);
    }

}
