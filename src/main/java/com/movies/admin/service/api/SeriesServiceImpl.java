package com.movies.admin.service.api;

import com.google.gson.reflect.TypeToken;
import com.movies.admin.helper.JsonHelper;
import com.movies.admin.model.Series;
import com.movies.admin.service.api.request.SeriesRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

public class SeriesServiceImpl extends ApiService implements SeriesService {

    @Override
    public List<Series> list() throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "series"))
                .method("GET", BodyPublishers.noBody());

        HttpResponse<String> response = this.sendRequest(requestBuilder);

        Type itemsListType = new TypeToken<List<Series>>() {}.getType();
        return JsonHelper.fromJsonByType(response.body(), itemsListType);
    }

    @Override
    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "series/" + id))
                .method("DELETE", BodyPublishers.noBody());

        this.sendRequest(requestBuilder);
    }

    @Override
    public void create(SeriesRequest seriesRequest) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "series"))
                .method("POST", BodyPublishers.ofString(JsonHelper.toJson(seriesRequest)));

        this.sendRequest(requestBuilder);
    }

    @Override
    public void update(SeriesRequest seriesRequest, Integer id) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url + "series/" + id))
                .method("PUT", BodyPublishers.ofString(JsonHelper.toJson(seriesRequest)));

        this.sendRequest(requestBuilder);
    }
}
