package com.testing;

import static io.restassured.RestAssured.get;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APICalls extends RestClient {
	public static Response updateResponse(String orderId, String status) {
		String URL = RestClient.URI + "/" + orderId + "/" + status;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response res = request.put(URL);
		return res;
	}
	
	public static Response getResponse(String orderId) {
		String URL = RestClient.URI  + "/" + orderId;
		Response res = get(URL);
		return res;
	}

	public static Response postRequest(JSONObject obj) {
		RequestSpecification req = RestAssured.given();
		req.header("Content-Type", "application/json");
		req.body(obj.toString());
		Response res = req.post(RestClient.URI );
		return res;
	}
	
	public static int getStatusCode(String orderId, String status) {
		int code = 0;
		Response res = updateResponse(orderId, status);
		code = res.getStatusCode();
		return code;
	}

}
