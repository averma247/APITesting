package com.testing.testclasses;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.testing.APICalls;
import com.testing.ResponseParser;
import com.testing.RestClient;
import com.testing.propertyreader.Propertyreader;

import io.restassured.response.Response;

public class CancelOrderTest extends RestClient {
	String status = "cancel";

	// Verify response status code is 200 when order gets cancelled
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		JSONObject requestObj = APICalls.getPayload("verifyValidResponse");
		Response res = APICalls.postRequest(requestObj);
		orderId = ResponseParser.getOrderId(res);
		RestClient.setOrderId(orderId);
		Response resCancel = APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(200, resCancel.getStatusCode());
	}

	// Verify response the status code is 404 if order does not exist
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		Response res = APICalls.updateResponse(String.valueOf(orderId) + 1050, status);
		Assert.assertEquals(404, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageOrderNotFound"),
				ResponseParser.getResponseValues(String.valueOf(orderId) + 1050, "message", status));
	}

	// Verify the response status code if status is already "CANCELLED" and user tries to make it "COMPLETE"
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessageForCancelled() throws Exception {
		APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageComplete"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", "complete"));
		Assert.assertEquals(422, APICalls.updateResponse(String.valueOf(orderId), "complete").getStatusCode());
	}

	// Verify the response status code if status is already "CANCELLED" and user
	// tries to make it "ONGOING"
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessageForOngoing() throws Exception {
		APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("message"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", "take"));
		Assert.assertEquals(422, APICalls.updateResponse(String.valueOf(orderId), "take").getStatusCode());
	}
}
