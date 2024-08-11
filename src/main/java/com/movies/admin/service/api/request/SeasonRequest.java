package com.movies.admin.service.api.request;

import com.movies.admin.model.Series;
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
public class SeasonRequest {
    public String img;
    public Status status;
    public Integer year;
    public Integer number;
    public Integer totalEpisodes;
    public Series series;
}
