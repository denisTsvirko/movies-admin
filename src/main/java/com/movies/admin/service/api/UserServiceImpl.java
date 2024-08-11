package com.movies.admin.service.api;

import com.google.gson.reflect.TypeToken;
import com.movies.admin.helper.JsonHelper;
import com.movies.admin.model.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

public class UserServiceImpl extends ApiService implements UserService {

    @Override
    public List<User> list() throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "users"))
                .method("GET", BodyPublishers.noBody());

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        Type itemsListType = new TypeToken<List<User>>() {}.getType();
        return JsonHelper.fromJsonByType(response.body(), itemsListType);
    }

    @Override
    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "users/" + id))
                .method("DELETE", BodyPublishers.noBody());

        this.sendRequest(requestBuilder);
    }
}
