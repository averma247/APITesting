package com.testing.testclasses;

import org.testng.annotations.Test;

import com.testing.APICalls;
import com.testing.ResponseParser;
import com.testing.RestClient;
import com.testing.propertyreader.Propertyreader;

import org.json.simple.JSONObject;
import org.testng.Assert;

import io.restassured.response.Response;

public class PlaceOrderTest extends RestClient {
	// Verify the status code of the order with valid values
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		
		JSONObject requestObject = RestClient.getPayload("verifyValidResponse");
		Response res = APICalls.postRequest(requestObject);
		orderId = ResponseParser.getOrderId(res);
		RestClient.setOrderId(orderId);
		Assert.assertEquals(201, res.getStatusCode());
	}

	// Verify the status code of the order with invalid values of origin or
	// destination
	@Test(priority = 2)
	public void verifyInValidResponse() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyInValidResponse");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(503, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("errorServiceMessage"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid values of 3 coordinates
	@Test(priority = 2)
	public void verifyInValidResponsePayload() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyResponseWithInvalidPayload2");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("errorMessageInavlidResponse"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid payload
	@Test(priority = 2)
	public void verifyResponseWithInvalidPayload() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyResponseWithInvalidPayload");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("errorMessageInavlidResponse"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify driving distance between each 2 stops is in metres
	@Test(priority = 2)
	public void verifyDistance() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyDrivingDistance");
		String st[] = new String[2];
		st = ResponseParser.getAttributeValueDistance(requestObj,"drivingDistancesInMeters");
		String dist[] = new String[2];
		dist[0] = Propertyreader.getTestDataPropertyValue("distance1");
		dist[1] = Propertyreader.getTestDataPropertyValue("distance2");
		int i = 0;
		for (String str : st) {
			Assert.assertEquals(str, dist[i]);
			++i;
		}
	}

	// Verify the count of driving distance array
	@Test(priority = 2)
	public void verifyDistanceValuesCount() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyDrivingDistanceCount");
		int count = ResponseParser.getDistanceValueCount(requestObj,"drivingDistancesInMeters");
		Assert.assertEquals(String.valueOf(count), Propertyreader.getTestDataPropertyValue("count"));
	}

	// Verify the currency of fare according to calculations
	@Test(priority = 3)
	public void verifyFareCurrency() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyCurrency");
		Response res=APICalls.postRequest(requestObj);
		String currency = ResponseParser.getFareValues(res.getBody().asString(), "currency");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("currency"), currency);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyAmount");
		Response res=APICalls.postRequest(requestObj);
		String amount = ResponseParser.getFareValues(res.getBody().asString(), "amount");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("amount"), amount);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare9to5() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyAmount9to5");
		Response res=APICalls.postRequest(requestObj);
		String amount = ResponseParser.getFareValues(res.getBody().asString(), "amount");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("amount9to5"), amount);
	}

	// Verify response when request is having past time
	@Test(priority = 3)
	public void VerifyBadRequest() throws Exception {
		JSONObject requestObj = RestClient.getPayload("VerifyBadRequest");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messagePastOrder"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// verify response when request is having longitude value 0
	@Test(priority = 3)
	public void VerifyBadRequestAttribute() throws Exception {
		JSONObject requestObj = RestClient.getPayload("VerifyAttributeBadRequest");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("errorMessage"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// verify response when request with future time also
	@Test(priority = 4)
	public void verifyValidResponseWithTime() throws Exception {
		JSONObject requestObj = RestClient.getPayload("verifyValidResponseWithTime");
		Response res = APICalls.postRequest(requestObj);
		Assert.assertEquals(201, res.getStatusCode());
	}

}
