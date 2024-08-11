package com.movies.admin.service.api.request;

import com.movies.admin.model.Genre;
import com.movies.admin.service.api.enums.Country;
import com.movies.admin.service.api.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesRequest {
    public String img;
    public String nameRu;
    public String nameEng;
    public Status status;
    public String premiereDate;
    public Country country;
    public Float ratingIMDb;
    public String description;
    public String story;
    public List<Genre> genres;
}
