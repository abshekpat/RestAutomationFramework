package com.api.helpers;

import com.api.models.request.CreateUserRequest;
import io.restassured.response.Response;

import java.util.List;

public class UserService extends BaseService{
     private final String BASE_PATH = "/user/";
     public Response CreateUserList(List<CreateUserRequest> payload){
        return postRequest(payload, BASE_PATH+"createWithList");
     }

     public Response getUser(String endpoint){
         return getRequest(BASE_PATH+endpoint);
     }
}
