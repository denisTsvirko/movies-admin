package com.movies.admin.service.api;

import com.movies.admin.model.Series;
import com.movies.admin.service.api.request.SeriesRequest;

import java.io.IOException;
import java.util.List;

public interface SeriesService {
    List<Series> list() throws IOException, InterruptedException;
    void delete(Integer id) throws IOException, InterruptedException;
    void create(SeriesRequest seriesRequest) throws IOException, InterruptedException;
    void update(SeriesRequest seriesRequest, Integer id) throws IOException, InterruptedException;
}
