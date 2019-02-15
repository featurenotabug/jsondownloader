package test.cases;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.logger.ConsoleLogger;
import service.logger.Logger;
import test.config.TestAppConfig;
import test.subjects.ExceptionSamples;
import test.subjects.JsonPlaceholderSamplePosts;
import test.utils.SystemOutputInterceptor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class LoggerTests {


    private Logger consoleLogger = new ConsoleLogger();

    @Test
    public void verifyStringProperlyLoggedToOut() {
        String testContent = JsonPlaceholderSamplePosts.getFirstPost().toString();
        String consoleOutput = SystemOutputInterceptor.captureConsoleOutAction(() -> consoleLogger.out(testContent));

        String errorMessage = "Message logged to console output differs from original message.";
        Assert.assertEquals(errorMessage, testContent, consoleOutput);
    }

    @Test
    public void verifyStringProperlyLoggedToError() {
        String testContent = JsonPlaceholderSamplePosts.getLastPost().toString();
        String consoleOutput = SystemOutputInterceptor.captureConsoleErrorAction(() -> consoleLogger.error(testContent));

        String errorMessage = "Message logged to console error differs from original message.";
        Assert.assertEquals(errorMessage, testContent, consoleOutput);
    }

    @Test
    public void verifyExceptionProperlyLoggedToError() {
        String expectedStackTraceString = ExceptionSamples.SAMPLE_STACKTRACE;
        Exception toLog = ExceptionSamples.SAMPLE_EXCEPTION;
        String consoleOutput = SystemOutputInterceptor.captureConsoleErrorAction(() -> consoleLogger.error(toLog));

        Assertions.assertThat(consoleOutput).isEqualToIgnoringNewLines(expectedStackTraceString);
    }
}
