package com.movies.admin.service.api;

import com.movies.admin.model.Season;
import com.movies.admin.service.api.request.SeasonRequest;

import java.io.IOException;
import java.util.List;

public interface SeasonService {
    List<Season> list(int seriesId) throws IOException, InterruptedException;
    void delete(Integer id) throws IOException, InterruptedException;
    void create(SeasonRequest seasonRequest) throws IOException, InterruptedException;
    void update(SeasonRequest seasonRequest, Integer id) throws IOException, InterruptedException;
}
