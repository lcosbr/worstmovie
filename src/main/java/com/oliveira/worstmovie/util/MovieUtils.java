package com.oliveira.worstmovie.util;

import com.oliveira.worstmovie.domain.Movie;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MovieUtils {

    /**
     * This method will create a new movie object with a new producer using an old reference
     *
     * @param oldMovie existing movie object
     * @param producer new producer
     * @return newMovie object
     */
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
