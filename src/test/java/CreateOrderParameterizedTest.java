import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.CancelOrderRequest;
import request.CreateOrderRequest;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    private Integer track;

    public CreateOrderParameterizedTest(String firstName, String lastName, String address, String station, String phone,
                                        Integer rentTime, String deliveryDate,
                                        String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = station;
        this.rentTime = rentTime;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] postTestData() {
        return new Object[][]{
                {"Ira", "Fil", "Svetlova, 12", "5", "+7 900 355 35 35", 4, "2024-12-06", "I am waiting",
                        List.of("BLACK", "GREY")},
                {"Ira", "Fil", "Svetlova, 12", "5", "+7 900 355 35 35", 4, "2024-12-06", "I am waiting",
                        List.of("BLACK")},
                {"Ira", "Fil", "Svetlova, 12", "5", "+7 900 355 35 35", 4, "2024-12-06", "I am waiting",
                        List.of("GREY")},
                {"Ira", "Fil", "Svetlova, 12", "5", "+7 900 355 35 35", 4, "2024-12-06", "I am waiting",
                        List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        track = null;
    }

    @Test
    @DisplayName("Check create order success")
    public void createParameterizedOrder() {
        CreateOrderRequest order = new CreateOrderRequest(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        track = callCreateOrder(order)
                .then()
                .assertThat()
                .log().all()
                .statusCode(Constants.CREATED_RESPONSE_CODE)
                .body(Constants.TRACK_FIELD, notNullValue())
                .extract().path(Constants.TRACK_FIELD);
    }

    @Step("Send POST request to /api/v1/orders with {order.color}")
    public Response callCreateOrder(CreateOrderRequest order) {
        return RestAssured.given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(order)
                .when()
                .post(Constants.API_V_1_ORDERS);
    }

    @After
    public void cancelOrder() {
        CancelOrderRequest cancelOrderRequest = new CancelOrderRequest(track);
        RestAssured.given()
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE)
                .body(cancelOrderRequest)
                .when()
                .log().all()
                .put(Constants.API_V_1_ORDERS_CANCEL);
    }


}



