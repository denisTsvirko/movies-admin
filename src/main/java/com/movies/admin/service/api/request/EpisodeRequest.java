package com.movies.admin.service.api.request;

import com.movies.admin.model.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeRequest {
    public String img;
    public Integer number;
    public String nameRu;
    public String nameEng;
    public String releaseDateRu;
    public String releaseDateEng;
    public Float ratingIMDb;
    public String description;
    public Season season;
}
