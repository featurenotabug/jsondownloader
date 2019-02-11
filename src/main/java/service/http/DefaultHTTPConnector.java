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
public class DefaultHTTPConnector implements HTTPConnector {

    @Override
    public String get(@NotNull String url) {
        try {
            return retrieveResponse(sendRequest(createRequest(url)));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); //exception handling left unimplemented
        }
    }

    private String retrieveResponse(@NotNull HttpResponse<String> httpResponse) throws IOException {
        if (isSuccessful(httpResponse.statusCode()))
            return httpResponse.body();
        else
            throw new IOException("Error while retrieving data via HTTP - expected response with success code (2xx), received:" + httpResponse.statusCode());
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createRequest(String url) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    private boolean isSuccessful(int httpResponseCode) {
        return Response.Status.Family.familyOf(httpResponseCode).equals(Response.Status.Family.SUCCESSFUL);
    }
}
