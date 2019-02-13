package configuration;

import model.PostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.downloader.DefaultDownloader;
import service.downloader.Downloader;
import service.output.GenericFileWriter;
import service.output.JsonFileWriter;
import service.output.Writer;
import service.dataprovider.ItemProvider;
import service.dataprovider.HttpJsonProvider;
import service.http.DefaultHTTPConnector;
import service.http.HTTPConnector;
import service.mapper.JacksonMapper;
import service.mapper.Mapper;

@Configuration
@PropertySource("classpath:application.properties")
public class DefaultAppConfig {

    @Value("${data.url}")
    private String dataSourceUrl;

    @Value("${output.dir}")
    private String outputDirectory;

    @Bean("httpPostsToFileDownloader")
    public Downloader httpPostsToFileDownloader() {
        return new DefaultDownloader<>(httpPostsProvider(), jsonFileWriter());
    }

    @Bean("genericHttpPostsDownloader")
    public Downloader genericHttpPostsDownloader() {
        return new DefaultDownloader<>(httpPostsProvider(), genericFileWriter());
    }

    @Bean("httpPostsProvider")
    public ItemProvider<PostDTO> httpPostsProvider() {
        return new HttpJsonProvider<>(dataSourceUrl, defaultConnector(), postsMapper());
    }

    @Bean("jsonFileWriter")
    public Writer<PostDTO> jsonFileWriter() {
        return new JsonFileWriter<>(outputDirectory, postsMapper());
    }

    @Bean("genericFileWriter")
    public Writer<Object> genericFileWriter() {
        return new GenericFileWriter(outputDirectory);
    }

    @Bean("postsMapper")
    public Mapper<PostDTO> postsMapper() {
        return new JacksonMapper<>(PostDTO.class);
    }

    @Bean("defaultConnector")
    public HTTPConnector defaultConnector(){
        return new DefaultHTTPConnector();
    }

}
