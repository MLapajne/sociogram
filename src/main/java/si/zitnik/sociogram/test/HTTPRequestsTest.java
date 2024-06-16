package si.zitnik.sociogram.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.util.api.SociogramAPIManager;
import si.zitnik.sociogram.util.api.reqres.ActivationRequest;
import si.zitnik.sociogram.util.api.reqres.ActivationResponse;
import si.zitnik.sociogram.util.api.reqres.TrialActiveRequest;
import si.zitnik.sociogram.util.api.reqres.TrialActiveResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPRequestsTest {

    private static void testActivation() throws IOException, InterruptedException, URISyntaxException {
        var requestData = new ActivationRequest("jnek-gaLo-T1zL-RsBz-ADtV", 2, null, null, null, null);

        var responseData = SociogramAPIManager.request(
                SociogramConstants.SOCIOGRAM_API_ACTIVATE,
                requestData,
                ActivationResponse.class
        );

        System.out.println("responseData = " + responseData);
    }

    private static void testIsTrial() throws IOException, InterruptedException, URISyntaxException {
        var requestData = new TrialActiveRequest("jnek-gaLo-T1zL-RsBz-ADtV");

        var responseData = SociogramAPIManager.request(
                SociogramConstants.SOCIOGRAM_API_TRIAL,
                requestData,
                TrialActiveResponse.class
        );

        System.out.println("responseData = " + responseData);
    }


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        testActivation();
        testIsTrial();
    }


}
