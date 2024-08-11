package com.movies.admin.service.api;

import com.movies.admin.model.Genre;
import com.movies.admin.service.api.request.GenreRequest;

import java.io.IOException;
import java.util.List;

public interface GenreService {
    List<Genre> list() throws IOException, InterruptedException;
    void delete(Integer id) throws IOException, InterruptedException;
    void create(GenreRequest genreRequest) throws IOException, InterruptedException;
    void update(GenreRequest genreRequest, Integer id) throws IOException, InterruptedException;
}
