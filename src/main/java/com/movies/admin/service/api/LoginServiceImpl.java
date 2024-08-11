package com.movies.admin.service.api;

import com.movies.admin.helper.JsonHelper;
import com.movies.admin.service.api.dto.Token;
import com.movies.admin.service.api.request.LoginRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class LoginServiceImpl extends ApiService implements LoginService {

    @Override
    public Token login(LoginRequest loginRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(url + "auth/login"))
            .method(
                "POST", 
                BodyPublishers.ofString(JsonHelper.toJson(loginRequest))
            );
            

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        return JsonHelper.fromJson(response.body(), Token.class);
    }
}
