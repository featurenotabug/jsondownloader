package service.http;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.Response;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
public final class DefaultHTTPConnector implements HTTPConnector {

    @Override
    public String getResponse(@NotNull String url) {
        try {
            return getResponseString(url);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); //exception handling left unimplemented
        }
    }

    private String getResponseString(String url) throws IOException, InterruptedException {
        return retrieveResponseBody(sendRequest(createRequest(url)));
    }

    private String retrieveResponseBody(@NotNull HttpResponse<String> httpResponse) throws IOException {
        if (isSuccessful(httpResponse.statusCode()))
            return httpResponse.body();
        else
            throw errorResponseCodeExceptionOf(httpResponse.statusCode());
    }

    private IOException errorResponseCodeExceptionOf(int statusCode){
        return new IOException("Error while retrieving data via HTTP - expected response with success code (2xx), received:" + statusCode);
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpClient getHttpClient(){
        return HttpClient.newBuilder().build();
    }

    private HttpRequest createRequest(String url) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    private boolean isSuccessful(int httpResponseCode) {
        return statusFamilyOf(httpResponseCode).equals(successfulStatusFamily()); // i.e. response code belongs to 200-299 range
    }

    private Response.Status.Family statusFamilyOf(int httpResponseCode){
        return Response.Status.Family.familyOf(httpResponseCode);
    }

    private Response.Status.Family successfulStatusFamily(){
        return Response.Status.Family.SUCCESSFUL;
    }

}
