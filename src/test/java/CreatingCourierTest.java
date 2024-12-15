import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import request.CreateCourierRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreatingCourierTest extends BaseCourierTest {

    public static final String FIRST_NAME = "Tany";

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @Test
    @DisplayName("Check create courier success")
    public void createCourier() {
        CreateCourierRequest courier = new CreateCourierRequest(login, password, FIRST_NAME);
        callCreateCourier(courier)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.OK_FIELD, equalTo(true))
                .and()
                .statusCode(Constants.CREATED_RESPONSE_CODE);
    }

    @Test
    @DisplayName("Check create courier with existing login fails")
    public void createTwoCouriersImpossible() {
        CreateCourierRequest courier = new CreateCourierRequest(login, password, FIRST_NAME);
        callCreateCourier(courier)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.OK_FIELD, equalTo(true))
                .and()
                .statusCode(Constants.CREATED_RESPONSE_CODE);

        callCreateCourier(courier)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.MESSAGE_FIELD, equalTo(Constants.ACCOUNT_EXIST_ERROR))
                .and()
                .statusCode(Constants.CONFLICT_RESPONSE_CODE);
    }

    @Test
    @DisplayName("Check create courier with missing password fails")
    public void createCourierWithMissingFields() {
        CreateCourierRequest courier = new CreateCourierRequest(login, null, null);
        callCreateCourier(courier)
                .then()
                .assertThat()
                .log().all()
                .body(Constants.MESSAGE_FIELD, equalTo(Constants.MISSING_FIELD_ERROR))
                .and()
                .statusCode(Constants.BAD_REQUEST_CODE);
    }

    @Step("Send POST request to /api/v1/courier")
    public Response callCreateCourier(CreateCourierRequest request) {
        return given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(request)
                .when()
                .post(Constants.API_V_1_COURIER);

    }
}