package com.oliveira.worstmovie.controller;

import com.oliveira.worstmovie.service.WorstMovieIntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.oliveira.worstmovie.util.AppConstants.RECEIVED_REQUEST;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/movies" )
public class WorstMovieController {

    private final WorstMovieIntervalService worstMovieIntervalService;

    /**
     * This method will process the interval request
     *
     * @return json response body with the Intervals calculated
     */
    @GetMapping(value = "/intervals", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object processGetIntervalRequest() {
        log.info(RECEIVED_REQUEST, RequestMethod.GET);
        return worstMovieIntervalService.process();
    }
}
