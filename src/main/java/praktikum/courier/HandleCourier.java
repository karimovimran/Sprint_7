package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;
import static praktikum.Constants.BASE_URL;
import static praktikum.Constants.DELETE_COURIER;
import static praktikum.Constants.POST_COURIER_CREATE;
import static praktikum.Constants.POST_COURIER_LOGIN;

public class HandleCourier {
    public static RequestSpecification requestSpec() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(CourierDetails courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_CREATE)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse courierAuthorization(RegisterCourier courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse courierDelete(int courierId) {
        return requestSpec()
                .when()
                .delete(DELETE_COURIER + courierId)
                .then();
    }
}