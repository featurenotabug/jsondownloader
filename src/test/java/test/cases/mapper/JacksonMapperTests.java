package test.cases.mapper;

import model.PostDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.http.HTTPConnector;
import service.mapper.CollectionMapper;
import test.config.TestAppConfig;
import test.subjects.JsonPlaceholderSamplePosts;


import java.util.List;

import org.assertj.core.api.Assertions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class JacksonMapperTests {

    @Autowired
    @Qualifier("postsMapper")
    private CollectionMapper<PostDTO> jacksonMapper;

    @Autowired
    @Qualifier("defaultConnector")
    private HTTPConnector httpConnector;

    @Value("${data.url}")
    String url;

    private static String httpResponseContent;

    @Before
    public void initialize(){
        if(httpResponseContent == null)
            httpResponseContent =  httpConnector.getResponse(url);
    }


    @Test
    public void verifySinglePostJsonSerializationAndDeserialization(){
        PostDTO post = JsonPlaceholderSamplePosts.getFirstPost();
        String jsonPost  = jacksonMapper.mapToString(post);
        PostDTO deserializedPost = jacksonMapper.mapFromString(jsonPost);

        Assert.assertEquals(post, deserializedPost);
    }

    @Test
    public void compareAllDownloadedPostsAfterMappingWithHttpResponseIgnoringWhitespaces(){
        List<PostDTO> deserializedPosts = jacksonMapper.mapToListFromString(httpResponseContent);
        String serializedPosts = jacksonMapper.mapToStringFromList(deserializedPosts);

        Assertions.assertThat(serializedPosts).isEqualToIgnoringWhitespace(httpResponseContent);
    }

    @Test
    public void compareAllDownloadedPostsAfterSerializingAndDeserializingFromHttpResponse(){
        List<PostDTO> deserializedPosts = jacksonMapper.mapToListFromString(httpResponseContent);
        String serializedPosts = jacksonMapper.mapToStringFromList(deserializedPosts);
        List<PostDTO> reDeserializedPosts = jacksonMapper.mapToListFromString(serializedPosts);

        Assert.assertEquals(deserializedPosts, reDeserializedPosts);
    }
}
