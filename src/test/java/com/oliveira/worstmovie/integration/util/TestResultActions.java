package com.oliveira.worstmovie.integration.util;

import com.oliveira.worstmovie.domain.PrizeInterval;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.ResultActions;

import static com.oliveira.worstmovie.util.AppConstants.MAX;
import static com.oliveira.worstmovie.util.AppConstants.MIN;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
public class TestResultActions {

    private final ResultActions resultActions;

    public TestResultActions andExpectSuccess() throws Exception {
        resultActions.andExpect(status().is2xxSuccessful());
        return this;
    }

    public TestResultActions andExpectMinField() throws Exception {
        resultActions.andExpect(content().string(containsString(MIN)));
        return this;
    }

    public TestResultActions andExpectMaxField() throws Exception {
        resultActions.andExpect(content().string(containsString(MAX)));
        return this;
    }

    public TestResultActions andExpectProducer(String producer) throws Exception {
        resultActions.andExpect(content().string(containsString("\"producer\":\"" + producer + "\"")));
        return this;
    }

    public TestResultActions andExpectInterval(String interval) throws Exception {
        resultActions.andExpect(content().string(containsString("\"interval\":" + interval)));
        return this;
    }

    public TestResultActions andExpectPreviousWin(String previousWin) throws Exception {
        resultActions.andExpect(content().string(containsString("\"previousWin\":" + previousWin)));
        return this;
    }

    public TestResultActions andExpectFollowingWin(String followingWin) throws Exception {
        resultActions.andExpect(content().string(containsString("\"followingWin\":" + followingWin)));
        return this;
    }

    public TestResultActions andExpectPrize(PrizeInterval prizeInterval) throws Exception {
        andExpectProducer(prizeInterval.getProducer());
        andExpectInterval(String.valueOf(prizeInterval.getInterval()));
        andExpectPreviousWin(String.valueOf(prizeInterval.getPreviousWin()));
        andExpectFollowingWin(String.valueOf(prizeInterval.getFollowingWin()));
        return this;
    }

    public TestResultActions andExpectClientError() throws Exception {
        resultActions.andExpect(status().is4xxClientError());
        return this;
    }

}