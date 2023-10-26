package apiauto;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiTest {
    @Test
    public void getAllDataUsers() {
        RestAssured.baseURI = "https://reqres.in/";
        int maxData = 6;

        given().when().get("api/users?page=1")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(maxData));
    }

    //Positive Test for GET
    @Test
    public void getSpecificUserByID() {
        RestAssured.baseURI = "https://reqres.in/";
        int idUser = 3;
        File jsonSchemaFile = new File("src/test/resources/jsonSchema/getSpecificUserByID.json");

        given().when().get("api/users/" + idUser)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchemaFile));
    }

    //Negative Test for GET
    @Test
    public void getSpecificUserByIDNegativeTest() {
        RestAssured.baseURI = "https://reqres.in/";
        int idUser = 190;

        given().when().get("api/users/" + idUser)
                .then()
                .assertThat().statusCode(404);
    }

    //Positive Test for POST
    @Test
    public void createNewUser() {
        RestAssured.baseURI = "https://reqres.in/";
        String first_name_value = "Redsky";
        String last_name_value = "Hunter";
        String email_value = "redskyHunter@mail.com";

        JSONObject inputObj = new JSONObject();
        inputObj.put("email", email_value);
        inputObj.put("first_name", first_name_value);
        inputObj.put("last_name", last_name_value);

        given()
                .headers("Content-Type", "application/json")
                .headers("Accept","application/json")
                .body(inputObj.toString())
                .post("api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("first_name", Matchers.equalTo(first_name_value))
                .assertThat().body("last_name", Matchers.equalTo(last_name_value))
                .assertThat().body("email", Matchers.equalTo(email_value))
                .assertThat().body("$", Matchers.hasKey("id"))
                .assertThat().body("$", Matchers.hasKey("createdAt"));

    }

    //Negative Test for POST
    @Test
    public void createNewUserNegativeTest() {
        RestAssured.baseURI = "https://reqres.in/";
        int first_name_value = 123;
        String last_name_value = "Redsky";
        String email_value = "redsky@mail.com";

        JSONObject inputObj = new JSONObject();
        inputObj.put("email", email_value);
        inputObj.put("first_name", first_name_value);
        inputObj.put("last_name", last_name_value);

        given()
                .post("api/users")
                .then().log().all()
                .assertThat().statusCode(415);

    }

    //Positive Test for PUT
//    @Test
//    public void updateNameUserByID() {
//        RestAssured.baseURI = "https://reqres.in/";
//        int userID = 2;
//        String nameUpdate = "updatedName";
//
//        String lname = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.last_name");
//        String email = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.email");
//        String avatar = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.avatar");
//
//        HashMap<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("id", userID);
//        bodyMap.put("first_name", nameUpdate);
//        bodyMap.put("last_name", lname);
//        bodyMap.put("email", email);
//        bodyMap.put("avatar", avatar);
//
//        JSONObject jsonObject = new JSONObject(bodyMap);
//
//        given()
//                .header("Content-Type","application/json")
//                .body(jsonObject.toString())
//                .put("api/users/" + userID)
//                .then()
//                .log().all()
//                .assertThat().statusCode(200)
//                .assertThat().body("first_name", Matchers.equalTo(nameUpdate));
//    }
//
//    //Negative Test for PUT
//    @Test
//    public void updateNameUserByIDNegativeTest() {
//        RestAssured.baseURI = "https://reqres.in/";
//        int userID = 2;
//        String nameUpdate = "updatedName";
//
//        String lname = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.last_name");
//        String email = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.email");
//        String avatar = given().when().get("api/users/" + userID).getBody().jsonPath().get("data.avatar");
//
//        HashMap<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("id", userID);
//        bodyMap.put("first_name", nameUpdate);
//        bodyMap.put("last_name", lname);
//        bodyMap.put("email", email);
//        bodyMap.put("avatar", avatar);
//
//        JSONObject jsonObject = new JSONObject(bodyMap);
//
//        given()
//                .header("Content-Type","application/json")
//                .put("api/users/" + userID)
//                .then()
//                .log().all()
//                .assertThat().statusCode(200)
//                .assertThat().body("first_name", Matchers.equalTo(nameUpdate));
//    }
}
