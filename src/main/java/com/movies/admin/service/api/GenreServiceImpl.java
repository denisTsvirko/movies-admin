package com.movies.admin.service.api;

import com.google.gson.reflect.TypeToken;
import com.movies.admin.helper.JsonHelper;
import com.movies.admin.model.Genre;
import com.movies.admin.service.api.request.GenreRequest;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

public class GenreServiceImpl extends ApiService implements GenreService {

    @Override
    public List<Genre> list() throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "genres"))
                .method("GET", BodyPublishers.noBody());

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        Type itemsListType = new TypeToken<List<Genre>>() {}.getType();
        return JsonHelper.fromJsonByType(response.body(), itemsListType);
    }

    @Override
    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "genres/" + id))
                .method("DELETE", BodyPublishers.noBody());

        this.sendRequest(requestBuilder);
    }

    @Override
    public void create(GenreRequest genreRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "genres"))
                .method("POST", BodyPublishers.ofString(JsonHelper.toJson(genreRequest)));

        this.sendRequest(requestBuilder);
    }

    @Override
    public void update(GenreRequest genreRequest, Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "genres/" + id))
                .method("PUT", BodyPublishers.ofString(JsonHelper.toJson(genreRequest)));

        this.sendRequest(requestBuilder);
    }
}
