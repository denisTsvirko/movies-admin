package com.movies.admin.service.api;

import com.movies.admin.helper.JsonHelper;
import com.movies.admin.service.api.request.RegistrationRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

public class RegistrationServiceImpl extends ApiService implements RegistrationService {

    @Override
    public void registration(RegistrationRequest registrationRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(url + "auth/register"))
            .method(
                "POST", 
                BodyPublishers.ofString(JsonHelper.toJson(registrationRequest))
            );
            

        this.sendRequest(requestBuilder);
    }
}
