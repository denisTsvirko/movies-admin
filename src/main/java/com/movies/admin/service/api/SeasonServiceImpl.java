package com.movies.admin.service.api;

import com.google.gson.reflect.TypeToken;
import com.movies.admin.helper.JsonHelper;
import com.movies.admin.model.Season;
import com.movies.admin.service.api.request.SeasonRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

public class SeasonServiceImpl extends ApiService implements SeasonService {

    @Override
    public List<Season> list(int seriesId) throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "series/" + seriesId + "/seasons"))
                .method("GET", BodyPublishers.noBody());

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        Type itemsListType = new TypeToken<List<Season>>() {}.getType();
        return JsonHelper.fromJsonByType(response.body(), itemsListType);
    }

    @Override
    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "seasons/" + id))
                .method("DELETE", BodyPublishers.noBody());

        this.sendRequest(requestBuilder);
    }

    @Override
    public void create(SeasonRequest seasonRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "seasons"))
                .method("POST", BodyPublishers.ofString(JsonHelper.toJson(seasonRequest)));

        this.sendRequest(requestBuilder);
    }

    @Override
    public void update(SeasonRequest seasonRequest, Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "seasons/" + id))
                .method("PUT", BodyPublishers.ofString(JsonHelper.toJson(seasonRequest)));

        this.sendRequest(requestBuilder);
    }
}
