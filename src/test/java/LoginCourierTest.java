import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import request.CreateCourierRequest;
import request.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends BaseCourierTest {

    public static final String NON_EXISTING_USERNAME = "nonExisting";
    public static final String EMPTY_PASSWORD = "";

    @Before
    public void createCourier() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        CreateCourierRequest courier = new CreateCourierRequest(login, password, "Tany");
        given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(courier)
                .when()
                .post(Constants.API_V_1_COURIER)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.OK_RESPONSE, equalTo(true))
                .and()
                .statusCode(Constants.CREATED_RESPONSE_CODE);
    }

    @Test
    @DisplayName("Check login success")
    public void loginCourier() {
        LoginRequest loginRequest = new LoginRequest(login, password);
        callLogin(loginRequest)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.ID_FIELD, notNullValue())
                .and()
                .statusCode(Constants.OK_RESPONSE_CODE);
    }

    @Test
    @DisplayName("Check login with invalid password fails")
    public void loginCourierInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest(login, "invalid");
        callLogin(loginRequest)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.MESSAGE_FIELD, equalTo(Constants.ACCOUNT_NOT_FOUND_ERROR))
                .and()
                .statusCode(Constants.NOT_FOUND_RESPONSE_CODE);
    }

    @Test
    @DisplayName("Check login courier with missing password fails")
    public void loginCourierMissingPassword() {
        LoginRequest loginRequest = new LoginRequest(login, EMPTY_PASSWORD);
        callLogin(loginRequest)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.MESSAGE_FIELD, equalTo(Constants.EMPTY_PASSWORD_ERROR))
                .and()
                .statusCode(Constants.BAD_REQUEST_CODE);
    }


    @Test
    @DisplayName("Check login with non existing user fails")
    public void loginCourierNonExistingUser() {
        LoginRequest loginRequest = new LoginRequest(NON_EXISTING_USERNAME, password);
        callLogin(loginRequest)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.MESSAGE_FIELD, equalTo(Constants.ACCOUNT_NOT_FOUND_ERROR))
                .and()
                .statusCode(Constants.NOT_FOUND_RESPONSE_CODE);
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response callLogin(LoginRequest request) {
        return given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(request)
                .when()
                .post(Constants.API_V_1_COURIER_LOGIN);
    }
}
