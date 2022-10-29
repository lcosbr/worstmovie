package com.oliveira.worstmovie.controller;

import com.oliveira.worstmovie.service.WorstMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.oliveira.worstmovie.util.AppConstants.RECEIVED_REQUEST;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WorstMovieController {

    private final WorstMovieService worstMovieService;

    @GetMapping(value = "/interval", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object processRequest() {
        log.info(RECEIVED_REQUEST);
        return worstMovieService.process();
    }
}
