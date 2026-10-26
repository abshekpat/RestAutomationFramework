package com.api.tests;

import com.api.helpers.UserService;
import com.api.models.request.CreateUserRequest;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class UserTest {

    Faker faker = new Faker();
    UserService userService = new UserService();
    String name;

    @Test
    public void createUserTest(){

        List<CreateUserRequest> userList = Arrays.asList(
                CreateUserRequest.builder()
                        .setId(faker.number().randomDigitNotZero())
                        .setUsername(faker.name().username())
                        .setFirstName(faker.name().firstName())
                        .setLastName(faker.name().lastName())
                        .setEmail(faker.internet().emailAddress())
                        .setPassword(faker.internet().password())
                        .setPhone(faker.phoneNumber().phoneNumber())
                        .setUserStatus(1)
                        .build()
        );


        Response response = userService.CreateUserList(userList);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        name = userList.get(0).getUsername();
    }

    @Test
    public void getUserNameTest() throws ParseException {
        Response response = userService.getUser("abhi");
        response.then().log().all();
        CreateUserRequest createUserResponse = response.as(CreateUserRequest.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(createUserResponse.getUsername(), "user1");



    }
}
