package com.oliveira.worstmovie.util;

import com.oliveira.worstmovie.domain.Movie;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MovieUtils {

    public static Movie movieCreate(Movie oldMovie, String producer){
        Movie newMovie = new Movie();
        newMovie.setYear(oldMovie.getYear());
        newMovie.setTitle(oldMovie.getTitle());
        newMovie.setStudios(oldMovie.getStudios());
        newMovie.setWinner(oldMovie.getWinner());
        newMovie.setProducers(producer);
        return newMovie;
    }
}
