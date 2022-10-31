package com.oliveira.worstmovie.service;

import com.google.gson.Gson;
import com.oliveira.worstmovie.dao.MoviesRepository;
import com.oliveira.worstmovie.domain.Movie;
import com.oliveira.worstmovie.domain.PrizeInterval;
import com.oliveira.worstmovie.util.MovieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.oliveira.worstmovie.util.AppConstants.ERROR_POPULATING_PRIZE_INTERVAL_LIST;
import static com.oliveira.worstmovie.util.AppConstants.ERROR_RETRIEVING_MOVIES_LIST;
import static com.oliveira.worstmovie.util.AppConstants.FINISHED_PROCESS;
import static com.oliveira.worstmovie.util.AppConstants.MAX;
import static com.oliveira.worstmovie.util.AppConstants.MIN;
import static com.oliveira.worstmovie.util.AppConstants.SEPARATOR_REGEX;
import static com.oliveira.worstmovie.util.AppConstants.STARTED_PROCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorstMovieIntervalService {

    private final static String GET_ALL_MOVIES = "Winner Interval";
    private final MoviesRepository moviesRepository;


    /**
     * Process method that will process the list of movies and process winner interval
     *
     * @return resultMap converted to Json
     */
    public Object process(){
        log.info(STARTED_PROCESS, GET_ALL_MOVIES);
        Set<Movie> moviesList = new LinkedHashSet<>();
        try {
            // Load only movies with Prizes and Split Producer Names
            moviesRepository.findAllWithPrizes().forEach(p -> splitProducers(p, moviesList));
        } catch (Exception e) {
            log.error(ERROR_RETRIEVING_MOVIES_LIST, e);
            throw e;
        }

        // Populate Interval list
        List<Movie> processIntervalList = new ArrayList<>(moviesList);
        Set<PrizeInterval> prizeIntervalList = new HashSet<>();
        moviesList.forEach(m -> populatePrizeInterval(m, processIntervalList, prizeIntervalList));

        // Search for min and max intervals
        final List<PrizeInterval> minPrizeIntervalList = getMinInterval(prizeIntervalList);
        final List<PrizeInterval> maxPrizeIntervalList = getMaxInterval(prizeIntervalList);

        // Adding Results to a map
        Map<String, List<PrizeInterval>> resultMap = new HashMap<>();
        resultMap.put(MIN, minPrizeIntervalList);
        resultMap.put(MAX, maxPrizeIntervalList);
        log.info(FINISHED_PROCESS);
        return new Gson().toJson(resultMap);
    }


    /**
     * Method that will populate the prize interval set
     *
     * @param movie movie object in evidence
     * @param processList the list that will be processed
     * @param prizeIntervalList the set that will receive the prize interval objects
     */
    private void populatePrizeInterval(Movie movie, List<Movie> processList, Set<PrizeInterval> prizeIntervalList){
        try {
            processList.remove(movie);
            final Movie comparingMovie = processList.stream()
                    .filter(m -> m.getProducers().equals(movie.getProducers()))
                    .findFirst()
                    .orElse(null);
            if (comparingMovie != null){
                int previousWin, followingWin;
                if (movie.getYear() > comparingMovie.getYear()) {
                    previousWin = comparingMovie.getYear();
                    followingWin = movie.getYear();
                } else {
                    previousWin = movie.getYear();
                    followingWin = comparingMovie.getYear();
                }
                final PrizeInterval prizeInterval = new PrizeInterval(
                        comparingMovie.getProducers(), previousWin, followingWin);
                prizeIntervalList.add(prizeInterval);
            }
        } catch (Exception e){
            log.error(ERROR_POPULATING_PRIZE_INTERVAL_LIST, e);
            throw e;
        }
    }


    /**
     * This method will get the min interval from the prize interval set
     *
     * @param prizeIntervalList prize interval set
     * @return arrayList of the min intervals found
     */
    private ArrayList<PrizeInterval> getMinInterval(Set<PrizeInterval> prizeIntervalList){
        final Optional<PrizeInterval> optPrizeInterval = prizeIntervalList.stream()
                .filter(p -> p.getInterval() > 0)
                .min(Comparator.comparing(PrizeInterval::getInterval));
        return addIntervalOnList(optPrizeInterval, prizeIntervalList);
    }

    /**
     * This method will get the max interval from the prize interval set.
     *
     * @param prizeIntervalList prize interval set
     * @return arrayList of the max intervals found
     */
    private ArrayList<PrizeInterval> getMaxInterval(Set<PrizeInterval> prizeIntervalList){
        final Optional<PrizeInterval> optPrizeInterval = prizeIntervalList.stream()
                .filter(p -> p.getInterval() > 0)
                .max(Comparator.comparing(PrizeInterval::getInterval));
        return addIntervalOnList(optPrizeInterval, prizeIntervalList);
    }

    /**
     * This method will add the interval on the set
     *
     * @param optPrizeInterval optional object of prize interval
     * @param prizeIntervalList prize interval set
     * @return newPrizeIntervalList arrayList of the intervals found
     */
    private ArrayList<PrizeInterval> addIntervalOnList(final Optional<PrizeInterval> optPrizeInterval,
                                                       Set<PrizeInterval> prizeIntervalList){
        final PrizeInterval prizeInterval;
        ArrayList<PrizeInterval> newPrizeIntervalList = new ArrayList<>();

        if(optPrizeInterval.isPresent()){
            prizeInterval = optPrizeInterval.get();
            prizeIntervalList.stream().filter( p -> p.getInterval() == prizeInterval.getInterval()).
                    forEach(newPrizeIntervalList::add);
        }

        return newPrizeIntervalList;
    }

    /**
     * This method will execute a regex to see if the producer field has more than one producer
     *
     * @param producer producer in evidence
     * @return boolean matching regex
     */
    private boolean checkMoreThanOneProducer(final String producer){
        final Pattern pattern = Pattern.compile(SEPARATOR_REGEX, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(producer);
        return matcher.find();
    }

    /**
     * This method will split the producers from the producer field
     *
     * @param movie object in evidence
     * @param moviesList movielist set
     */
    private void splitProducers(Movie movie, Set<Movie> moviesList){
        if(checkMoreThanOneProducer(movie.getProducers())){
            List<String> producers = Arrays.asList(movie.getProducers().split(SEPARATOR_REGEX));
            producers.forEach(p -> duplicateMovie(p, movie, moviesList));
        } else {
            moviesList.add(movie);
        }
    }

    /**
     * This method will duplicate the object movie with a separated producer
     *
     * @param splittedProducer split producer
     * @param movie movie in evidence
     * @param moviesList movies set
     */
    private void duplicateMovie(final String splittedProducer, Movie movie, Set<Movie> moviesList){
        moviesList.add(MovieUtils.movieCreate(movie, splittedProducer));
    }
}
