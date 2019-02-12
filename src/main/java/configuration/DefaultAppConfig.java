package configuration;

import model.PostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.downloader.DefaultDownloader;
import service.downloader.Downloader;
import service.output.ConsoleWriter;
import service.output.JsonFileWriter;
import service.output.Writer;
import service.provider.ItemProvider;
import service.provider.HttpJsonProvider;
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
        return new DefaultDownloader<PostDTO>(httpPostsProvider(), fileWriter());
    }

    @Bean("httpPostsProvider")
    public ItemProvider httpPostsProvider() {
        return new HttpJsonProvider<>(dataSourceUrl, defaultConnector(), postsMapper());
    }

    @Bean("fileWriter")
    public Writer fileWriter() {
        return new JsonFileWriter<>(outputDirectory, postsMapper());
    }

    @Bean("postsMapper")
    public Mapper postsMapper() {
        return new JacksonMapper<>(PostDTO.class);
    }

    @Bean("defaultConnector")
    public HTTPConnector defaultConnector(){
        return new DefaultHTTPConnector();
    }

    @Bean("consoleWriter")
    public Writer consoleWriter() {
        return new ConsoleWriter<>();
    }
}
