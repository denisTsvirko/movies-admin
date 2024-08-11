package com.movies.admin.service.api;

import com.google.gson.reflect.TypeToken;
import com.movies.admin.helper.JsonHelper;
import com.movies.admin.model.Episode;
import com.movies.admin.service.api.request.EpisodeRequest;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

public class EpisodeServiceImpl extends ApiService implements EpisodeService {

    @Override
    public List<Episode> list(int seasonId) throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "seasons/" + seasonId + "/episodes"))
                .method("GET", BodyPublishers.noBody());

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        Type itemsListType = new TypeToken<List<Episode>>() {}.getType();
        return JsonHelper.fromJsonByType(response.body(), itemsListType);
    }

    @Override
    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "episodes/" + id))
                .method("DELETE", BodyPublishers.noBody());

        this.sendRequest(requestBuilder);
    }

    @Override
    public void create(EpisodeRequest episodeRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "episodes"))
                .method("POST", BodyPublishers.ofString(JsonHelper.toJson(episodeRequest)));

        this.sendRequest(requestBuilder);
    }

    @Override
    public void update(EpisodeRequest episodeRequest, Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "episodes/" + id))
                .method("PUT", BodyPublishers.ofString(JsonHelper.toJson(episodeRequest)));

        this.sendRequest(requestBuilder);
    }
}
