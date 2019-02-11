package configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.downloader.Downloader;
import service.downloader.JsonDownloader;
import service.http.DefaultHTTPConnector;
import service.http.HTTPConnector;

@Configuration
@PropertySource("classpath:application.properties")
public class DefaultAppConfig {

    @Value("${data.url}")
    private String url;

    @Bean("postsDownloader")
    public Downloader postsDownloader() {
        return new JsonDownloader(url, defaultConnector());
    }

    @Bean("defaultConnector")
    public HTTPConnector defaultConnector(){
        return new DefaultHTTPConnector();
    }

}
