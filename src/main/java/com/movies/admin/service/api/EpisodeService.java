package com.movies.admin.service.api;

import com.movies.admin.model.Episode;
import com.movies.admin.service.api.request.EpisodeRequest;

import java.io.IOException;
import java.util.List;

public interface EpisodeService {
    List<Episode> list(int seasonId) throws IOException, InterruptedException;
    void delete(Integer id) throws IOException, InterruptedException;
    void create(EpisodeRequest episodeRequest) throws IOException, InterruptedException;
    void update(EpisodeRequest episodeRequest, Integer id) throws IOException, InterruptedException;
}
