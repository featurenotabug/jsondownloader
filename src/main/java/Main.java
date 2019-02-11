import configuration.DefaultAppConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import service.downloader.Downloader;
import service.provider.ItemProvider;

public class Main {

    private static final String DOWNLOADER_BEAN_NAME = "defaultDownloader";

    public static void main(String[] args) {
        try(AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(DefaultAppConfig.class)) {
            Downloader downloader = ctx.getBean(DOWNLOADER_BEAN_NAME, Downloader.class);
            downloader.download();
        }
    }
}
