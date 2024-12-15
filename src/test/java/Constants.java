public class Constants {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String OK_FIELD = "ok";
    public static final String MESSAGE_FIELD = "message";
    public static final String TRACK_FIELD = "track";
    public static final String ORDERS_FIELD = "orders";
    public static final String ID_FIELD = "id";
    public static final int OK_RESPONSE_CODE = 200;
    public static final int CREATED_RESPONSE_CODE = 201;
    public static final int CONFLICT_RESPONSE_CODE = 409;
    public static final int NOT_FOUND_RESPONSE_CODE = 404;
    public static final int BAD_REQUEST_CODE = 400;
    public static final String ACCOUNT_EXIST_ERROR = "Этот логин уже используется. Попробуйте другой.";
    public static final String MISSING_FIELD_ERROR = "Недостаточно данных для создания учетной записи";
    public static final String EMPTY_PASSWORD_ERROR = "Недостаточно данных для входа";
    public static final String ACCOUNT_NOT_FOUND_ERROR = "Учетная запись не найдена";
    public static final String CONTENT_TYPE_KEY = "Content-type";
    public static final String CONTENT_TYPE_VALUE = "application/json";
    public static final String API_V_1_COURIER = "/api/v1/courier";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";
    public static final String API_V_1_ORDERS = "/api/v1/orders";
    public static final String API_V_1_ORDERS_CANCEL = "/api/v1/orders/cancel";
    public static final String OK_RESPONSE = "ok";
}
