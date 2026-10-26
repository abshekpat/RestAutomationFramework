package com.api.helpers;

import static io.restassured.RestAssured.*;

import com.api.utils.FrameworkConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

public class BaseService {

    private static FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
    private static final String BASE_URL = config.url();
    private RequestSpecification requestSpecification;

    public BaseService(){
        requestSpecification = given().baseUri(BASE_URL);
    }

    protected Response postRequest(Object payload, String endpoint){
        return requestSpecification.contentType(ContentType.JSON).body(payload).log().all().post(endpoint);
    }

    protected Response getRequest(String endpoint){
        return requestSpecification.contentType(ContentType.JSON).log().all().get(endpoint);
    }
}
