package com.movies.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
    public int id;
    public Integer number;
    public String img;
    public String nameRu;
    public String nameEng;
    public String releaseDateRu;
    public String releaseDateEng;
    public float ratingIMDb;
    public String description;
}
