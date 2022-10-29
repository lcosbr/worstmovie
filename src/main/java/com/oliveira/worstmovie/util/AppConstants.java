package com.oliveira.worstmovie.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppConstants {

    public static String RECEIVED_REQUEST = "Request received";

    public static String ERROR_RETRIEVING_MOVIES_LIST = "An unexpected error has been occurred when trying to retrive Movies list data: ";
    public static String ERROR_POPULATING_PRIZE_INTERVAL_LIST = "An unexpected error has been occurred when trying to retrive Movies list data: ";
    public static String STARTED_PROCESS = "Started processing Movies List data";
    public static String FINISHED_PROCESS = "Finished processing Movies List data";

    public static String SEPARATOR_REGEX = "((,)\\s(and)\\s|\\s(and)\\s|(,)\\s)";
}

