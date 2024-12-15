import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.After;
import request.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseCourierTest {

    protected String login = "Cour";
    protected String password = "Qwerty";

    @After
    public void deleteCourier() {
        LoginRequest login = new LoginRequest(this.login, password);
        ExtractableResponse<Response> response = given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(login)
                .when()
                .post(Constants.API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .log().all()
                .extract();
        if (response.statusCode() == Constants.OK_RESPONSE_CODE) {
            Integer courierId = response.path("id");
            System.out.println("Удаляем курьера");
            given()
                    .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                    .when()
                    .delete("/api/v1/courier/" + courierId)
                    .then()
                    .assertThat()
                    .log().all()
                    .body(Constants.OK_FIELD, equalTo(true))
                    .and()
                    .statusCode(Constants.OK_RESPONSE_CODE);
        }
    }
}
