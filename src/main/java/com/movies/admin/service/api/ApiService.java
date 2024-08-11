package com.movies.admin.service.api;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.movies.admin.App;
import com.movies.admin.exceprion.BadRequestException;
import com.movies.admin.exceprion.UnauthorizedException;


public class ApiService {

    String url = "http://localhost:8080/api/";

    private final String token = App.getToken();

    public HttpResponse<String> sendRequest(HttpRequest.Builder requestBuilder) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .followRedirects(Redirect.NORMAL)
            .build();

        if (this.token != null) {
            requestBuilder.header("Authorization", "Bearer " + this.token);
        }


        HttpRequest request = requestBuilder
            .header("Content-Type", "application/json")
            .build();
     
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int code = response.statusCode();
        if(code >= 400) {
            createException(code, response.body());
        }
       
        return response;
    }

    private void createException(int code, String body)
    {
        if(code == 401) {
            throw new UnauthorizedException("Unauthorized problem");
        } else {
            throw new BadRequestException("Request problem error: " + body);
        }
    }
}
