package com.gagan.creditcard.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gagan.creditcard.api.response.ApiResponse;
import com.gagan.creditcard.api.response.ResponseStatus;
import com.gagan.creditcard.constant.Constants;
import com.gagan.creditcard.model.Card;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CardControllerTest {

	private RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int port;

	Card card;

	@BeforeEach
	public void setUp() {
		card = new Card("TestUser", "4769 9765 4321 0010", new BigDecimal(1110.22));
	}

	private String getBaseURL() {
		return "http://localhost:" + port;
	}

	private String getAPIEndpoint() {
		return getBaseURL() + "/api/v1/cards/";
	}

	@Test
	public void testAddCardResourceWithValidDetails() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Card> requestEntity = new HttpEntity<Card>(card, headers);

		ResponseEntity<ApiResponse> responseEntity = restTemplate.postForEntity(getAPIEndpoint(), requestEntity,
				ApiResponse.class);

		ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertTrue(apiResponse.getStatus().equals(ResponseStatus.SUCCESS.getStatusName()));
	}

	@Test
	public void testAddCardResourceWithInvalidCardNumber() {
		card.setCardNumber("2a2");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Card> requestEntity = new HttpEntity<Card>(card, headers);

		ResponseEntity<Object> responseEntity = restTemplate.postForEntity(getAPIEndpoint(), requestEntity,
				Object.class);

		Map<String, String> map = (Map<String, String>) responseEntity.getBody();
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertTrue(map.get("status").equals(ResponseStatus.ERROR.getStatusName()));
		assertEquals(map.get("data"), Constants.Message.INVALID_CARD_NUMBER);
	}

	@Test
	public void testAddCardResourceWithMoreThan19LengthCardNumber() {
		card.setCardNumber(card.getCardNumber() + "111111");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Card> requestEntity = new HttpEntity<Card>(card, headers);

		assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.postForEntity(getAPIEndpoint(), requestEntity, Object.class);
		});
	}

	@Test
	public void testAddCardResourceWithoutLimit() {
		card.setLimit(null);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Card> requestEntity = new HttpEntity<Card>(card, headers);

		assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.postForEntity(getAPIEndpoint(), requestEntity, Object.class);
		});

	}

	@Test
	public void testGetAllCards() {

		ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(getAPIEndpoint(), ApiResponse.class);

		ApiResponse apiResp = (ApiResponse) responseEntity.getBody();
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(apiResp.getStatus(), ResponseStatus.SUCCESS.getStatusName());
		assertTrue(apiResp.getData() instanceof List);
	}

	@Test
	public void testAddDuplicateCardResource() {
		card.setCardNumber("012345678903555");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Card> requestEntity = new HttpEntity<Card>(card, headers);

		ResponseEntity<ApiResponse> responseEntity = restTemplate.postForEntity(getAPIEndpoint(), requestEntity,
				ApiResponse.class);

		ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertTrue(apiResponse.getStatus().equals(ResponseStatus.SUCCESS.getStatusName()));

		responseEntity = restTemplate.postForEntity(getAPIEndpoint(), requestEntity, ApiResponse.class);
		apiResponse = (ApiResponse) responseEntity.getBody();
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertTrue(apiResponse.getStatus().equals(ResponseStatus.ERROR.getStatusName()));
	}
}
