package com.oliveira.worstmovie.integration.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@TestComponent
public class TestMockMvc {

    private static final String ENDPOINT = "/movies/intervals";

    @Autowired
    private MockMvc mockMvc;

    public TestResultActions sendIntervalRequest() throws Exception {
        return new TestResultActions(
                this.mockMvc.perform(get(ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    public TestResultActions sendUnsupportedPostRequest() throws Exception {
        return new TestResultActions(
                this.mockMvc.perform(post(ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    public TestResultActions sendUnsupportedPutRequest() throws Exception {
        return new TestResultActions(
                this.mockMvc.perform(put(ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    public TestResultActions sendUnsupportedDeleteRequest() throws Exception {
        return new TestResultActions(
                this.mockMvc.perform(delete(ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    public TestResultActions sendUnsupportedPatchRequest() throws Exception {
        return new TestResultActions(
                this.mockMvc.perform(patch(ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }
}