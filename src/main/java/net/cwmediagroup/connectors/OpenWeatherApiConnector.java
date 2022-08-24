package net.cwmediagroup.connectors;

import net.cwmediagroup.dataobjects.LocationObject;
import net.cwmediagroup.services.EnvService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OpenWeatherApiConnector {

    private final String baseUrl = EnvService.getEnvValue("BASE_URL");
    private final String appId = EnvService.getEnvValue("APP_ID");
    public final String separator = "&";


    public JSONObject getLocationData(LocationObject locationObject) throws RuntimeException {
        JSONObject jsonObject = new JSONObject();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(
                    buildUrl(locationObject.getLocationLat(), locationObject.getLocationLong())
            );
            request.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("HTTP request failed. HTTP error code: "
                        + response.getStatusLine().getStatusCode() + "\n URI: " + request.getURI()
                );
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output = br.readLine();
            if (output != null) {
                jsonObject = createJsonObject(output);
            }

            httpClient.getConnectionManager().shutdown();

            return jsonObject;
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String buildUrl(String locationLat, String locationLong) {
        String Url = "";

        return Url.concat(this.baseUrl)
                .concat("lat=")
                .concat(locationLat)
                .concat(separator)
                .concat("lon=")
                .concat(locationLong)
                .concat(separator)
                .concat("appid=")
                .concat(appId);
    }

    private JSONObject createJsonObject(String inputString) {
        return new JSONObject(inputString);
    }

}
