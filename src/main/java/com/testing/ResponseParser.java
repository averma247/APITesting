package com.testing;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import io.restassured.response.Response;

public class ResponseParser extends RestClient {
	public static String[] getAttributeValueDistance(JSONObject obj,String attribute ) {
		String jsonResponse = APICalls.postRequest(obj).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(jsonResponse);
		Object value = object.get("drivingDistancesInMeters");
		String st = value.toString();
		st.substring(1, st.length() - 1);
		String s1[] = st.substring(1, st.length() - 1).split(",");
		return s1;
	}

	public static int getDistanceValueCount(JSONObject obj,String attribute) {
		String jsonResponse = APICalls.postRequest(obj).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(jsonResponse);
		Object distanceValues = object.get("drivingDistancesInMeters");
		String distanceValuesString = distanceValues.toString();
		distanceValuesString.substring(1, distanceValuesString.length() - 1);
		String distances[] = distanceValuesString.substring(1, distanceValuesString.length() - 1).split(",");
		return distances.length;
	}

	public static String getOrderId(Response res) {
		String jsonResponse = res.getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(jsonResponse);
		Object responseOrderIdValue = object.get("id");
		String orderId = responseOrderIdValue.toString();
		return orderId;
	}

	public static String getFareValues(String jsonResponse, String attribute) {
		JSONObject mainObject = (JSONObject) JSONValue.parse(jsonResponse);
		Object mainFieldValue = mainObject.get("fare");
		String fare = mainFieldValue.toString();
		JSONObject subFieldJsonObject = (JSONObject) JSONValue.parse(fare);
		Object subFieldObject = subFieldJsonObject.get(attribute);
		String responseFieldValue = subFieldObject.toString();
		return responseFieldValue;
	}

	public static String getMessage(String jsonResponse) {
		JSONObject object = (JSONObject) JSONValue.parse(jsonResponse);
		Object value = object.get("message");
		String message = value.toString();
		return message.toString();
	}
	public static String getResponseValues(String orderId, String attribute, String statusURI) {
		String strJson = APICalls.updateResponse(orderId, statusURI).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get(attribute);
		String status = value.toString();
		return status;
	}
}
