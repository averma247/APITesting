package com.testing;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.testing.propertyreader.Propertyreader;

public class RestClient {

               private static String propertyValue;

               public static String URI;
               public static String orderId;
               public static Propertyreader propertyReader;

               public static void setOrderId(String id) {
                              orderId = id;
               }

               public static JSONObject getPayload(String testCaseName) throws Exception {

                              if (propertyReader==null){
                                             propertyReader = new Propertyreader();
                              }
                              propertyReader.initializeProperties();

                              URI = propertyReader.getConfigPropertyValue("URI") + propertyReader.getConfigPropertyValue("APIEndPoint");

                              propertyValue = propertyReader.getTestDataPropertyValue(testCaseName);

                              JSONParser jsonParser = new JSONParser();
                              JSONObject object = (JSONObject) jsonParser.parse(propertyValue);
                              return object;
               }

}


