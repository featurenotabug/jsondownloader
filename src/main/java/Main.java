import configuration.DefaultAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import service.downloader.Downloader;

public class Main {

    private static final String DOWNLOADER_BEAN_NAME = "postsDownloader";

    public static void main(String[] args) {
        try(AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(DefaultAppConfig.class)) {
            Downloader downloader = ctx.getBean(DOWNLOADER_BEAN_NAME, Downloader.class);
            downloader.getItems();
        }
    }
}
