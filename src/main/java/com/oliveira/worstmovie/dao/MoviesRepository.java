package com.oliveira.worstmovie.dao;

import com.oliveira.worstmovie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @Query("SELECT m FROM Movie m WHERE m.winner = 'yes' ORDER BY m.year ASC")
    List<Movie> findAllWithPrizes();

}
