package com.testing.testclasses;

import org.testng.annotations.Test;

import com.testing.APICalls;
import com.testing.ResponseParser;
import com.testing.RestClient;
import com.testing.propertyreader.Propertyreader;

import java.util.logging.Logger;

import org.testng.Assert;

import io.restassured.response.Response;

public class CompleteOrderTest extends RestClient {
	String status = "complete";
	private static Logger logger = Logger.getLogger("CompleteOrderTest"); 
	// Verify response status code is 200 when the order gets completed
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		logger.info("Verify response status code is 200 when the order gets completed");
		Response res = APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(200, res.getStatusCode());
	}

	// Verify response status code is 404 with invalid order id
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		logger.info("Verify response status code is 404 with invalid order id");
		Response res = APICalls.updateResponse("0", status);
		Assert.assertEquals(404, res.getStatusCode());
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageOrderNotFound"),
				ResponseParser.getResponseValues("0", "message", status));
	}

	// Verify response the status code is 422 if user tries to complete the order more than 1 time
	@Test(priority = 3)
	public void verifyInvalidFlowResponse() throws Exception {
		logger.info("y response the status code is 422 if user tries to complete the order more than 1 time");
		APICalls.updateResponse(String.valueOf(orderId), status);
		Assert.assertEquals(422, APICalls.getStatusCode(String.valueOf(orderId), status));
	}

	// Verify response message if user tries to complete the order more than 1 time
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessage() throws Exception {
		logger.info("Verify response message if user tries to complete the order more than 1 time");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageComplete"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", "complete"));
	}

	// Verify the message if status is already "COMPLETED" and user tries to make it "Cancelled"
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessageForCancelled() throws Exception {
		logger.info("Verify the message if status is already COMPLETED and user tries to make it Cancelled");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("messageAlreadyComplete"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", "cancel"));
		Assert.assertEquals(422, APICalls.getStatusCode(String.valueOf(orderId), status));
	}

	// Verify the message if status is already "COMPLETED" and user tries to make it "ONGOING"
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessageForOngoing() throws Exception {
		logger.info("Verify the message if status is already COMPLETED and user tries to make it ONGOING");
		Assert.assertEquals(Propertyreader.getTestDataPropertyValue("message"),
				ResponseParser.getResponseValues(String.valueOf(orderId), "message", "take"));
		Assert.assertEquals(422, APICalls.getStatusCode(String.valueOf(orderId), status));
	}
}
