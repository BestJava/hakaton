package ru.quantum.rest;

import java.io.IOException;
import java.net.URI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import ru.quantum.dto.*;

public class RestClient implements AutoCloseable {
    private URI uri;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private Gson gson = new Gson();

    public RestClient(URI restUri) {
        uri = restUri;
    }

    @Override
    public void close() throws Exception {
        httpClient.close();
    }

    public LoginResponse login(LoginRequest request) {
        String requestString = gson.toJson(request, new TypeToken<LoginRequest>(){}.getType());
        String responseString = executeRequest(requestString);
        return gson.fromJson(responseString, LoginResponse.class);
    }

    private String executeRequest(String requestString) {
        try {
            HttpPost request = new HttpPost(uri);
            request.addHeader("accept", "application/json");
            StringEntity reqEntity = new StringEntity(requestString);
            reqEntity.setContentType("application/json");
            request.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Http error with code: " + response.getStatusLine().getStatusCode());
            }

            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            throw new RuntimeException("Exception: " + e.toString());
        }        
    }
}
