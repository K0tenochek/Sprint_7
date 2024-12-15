import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check get orders returns list of orders")
    public void getListOrderTest() {
        Response response = callListOrder();
        response.then()
                .assertThat()
                .body(Constants.ORDERS_FIELD, notNullValue())
                .body(Constants.ORDERS_FIELD, Matchers.not(Matchers.empty()))
                .and()
                .statusCode(Constants.OK_RESPONSE_CODE);
    }

    @Step("Send GET request to /api/v1/orders")
    public Response callListOrder() {
        return given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .when()
                .get(Constants.API_V_1_ORDERS);
    }


}
