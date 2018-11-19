package com.testing.testclasses;

import org.testng.annotations.Test;

import com.testing.APICalls;
import com.testing.ResponseParser;
import com.testing.RestClient;
import com.testing.propertyreader.Propertyreader;

import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TakeOrderTest extends RestClient {
	String status = "take";

	// Verify response status code is 200 with valid order id
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		Response res = APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(200, res.getStatusCode());
	}

	// Verify response the status code is 404 if order does not exist
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		Assert.assertEquals(404, APICalls.getStatusCode("0", status));
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageOrderNotFound"),
				ResponseParser.getResponseValues("0", "message", status));
	}

	// Verify response the status code is 422 if with custom message if logic flow
	// is violated
	@Test(priority = 3)
	public void verifyInvalidFlowResponse() throws Exception {
		APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(422, APICalls.getStatusCode(String.valueOf(orderId), status));
	}

	// Verify the message if status is already "ONGOING" and again makes it's status
	// as ongoing
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessage() throws Exception {
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("message"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", status));
	}

}
