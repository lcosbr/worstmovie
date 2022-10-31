package com.oliveira.worstmovie.integration;

import com.oliveira.worstmovie.domain.PrizeInterval;
import com.oliveira.worstmovie.integration.util.TestMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Import(value = { TestMockMvc.class })
public class WorstMovieApplicationTests {

	@Autowired
	private TestMockMvc testMockMvc;

	@Test
	public void testSuccessIntervalRequest() throws Exception {
		final PrizeInterval minPrizeInterval = new PrizeInterval("Joel Silver", 1990, 1991);
		final PrizeInterval maxPrizeInterval = new PrizeInterval("Matthew Vaughn", 2002, 2015);

		// Sending a request to the application and checking the response
		testMockMvc.sendIntervalRequest()
				.andExpectSuccess()
				.andExpectMinField()
				.andExpectPrize(minPrizeInterval)
				.andExpectMaxField()
				.andExpectPrize(maxPrizeInterval);
	}

	@Test
	public void testUnsupportedPostRequest() throws Exception {
		testMockMvc.sendUnsupportedPostRequest()
				.andExpectClientError();
	}

	@Test
	public void testUnsupportedPutRequest() throws Exception {
		testMockMvc.sendUnsupportedPutRequest()
				.andExpectClientError();
	}

	@Test
	public void testUnsupportedDeleteRequest() throws Exception {
		testMockMvc.sendUnsupportedDeleteRequest()
				.andExpectClientError();
	}

	@Test
	public void testUnsupportedPatchRequest() throws Exception {
		testMockMvc.sendUnsupportedPatchRequest()
				.andExpectClientError();
	}
}
