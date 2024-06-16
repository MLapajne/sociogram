package si.zitnik.sociogram.util.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.zitnik.sociogram.config.SociogramConstants;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SociogramAPIManager {

    public static <T> T request(String apiEndpoint, Object requestData, Class<T> responseDataType) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiEndpoint))
                .timeout(Duration.of(SociogramConstants.SOCIOGRAM_API_TIMEOUT_SECONDS, SECONDS))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(requestData)
                ))
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        var response_data = objectMapper.readValue(response.body(), responseDataType);

        return response_data;
    }
}
