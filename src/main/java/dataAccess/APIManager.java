package dataAccess;

import exceptions.FailedFetchException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class APIManager {

    public String request(String endpoint) {
        try {
            return fetch(endpoint);
        } catch (FailedFetchException e) {
            e.printStackTrace();
            System.out.println("Error: Data is unavailable.");
        }
        return "";
    }

    private String fetch(String endpoint) throws FailedFetchException {
        String result = "";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.football-data.org/v2/" + endpoint)
                .get()
                .addHeader("X-Auth-Token", System.getenv("TOKEN"))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200)
                result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new FailedFetchException();
        }


        return result;
    }
}
