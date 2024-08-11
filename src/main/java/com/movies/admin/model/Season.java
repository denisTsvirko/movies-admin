package com.movies.admin.model;

import com.movies.admin.service.api.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season {
    public int id;
    public Status status;
    public Integer year;
    public Integer number;
    public String img;
    public Integer totalEpisodes;
}
