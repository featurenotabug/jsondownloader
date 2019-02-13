import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import service.downloader.Downloader;

@ComponentScan(basePackages = "configuration")
public class Application {

    @Autowired
    //@Qualifier("genericHttpPostsDownloader")
    //@Qualifier("httpPostsToConsoleDownloader")
    @Qualifier("httpPostsToJsonFileDownloader")
    private Downloader downloader;

    private void start(){
        downloader.download();
    }

    public static void main(String[] args) {
        try(AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class)) {
            Application app = ctx.getBean(Application.class);
            app.start();
        }
    }
}
