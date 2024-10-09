package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static praktikum.Constants.*;

public class OrderPhase {
    public static RequestSpecification requestSpecification() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Создание заказа")
    public ValidatableResponse successOrderCreate(OrderData orderInfo) {
        return requestSpecification()
                .body(orderInfo)
                .when()
                .post(POST_ORDER_CREATE)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancellationOrder(int track) {
        return requestSpecification()
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return requestSpecification()
                .when()
                .get(GET_ORDERS_LIST)
                .then();
    }
}
