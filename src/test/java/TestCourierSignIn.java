import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.*;

public class TestCourierSignIn {
    int createdCourierId;
    protected GetRandomCourier getRandomCourierDataGenerator = new GetRandomCourier();
    private CourierDetails courierDetails;
    protected HandleCourier courierActions;
    private AddCourier courierAssertions;
    private RegisterCourier courierLoginCredentials;

    @Before
    @Step("Генерация тестовых данных для курьера")
    public void setUp() {
        courierActions = new HandleCourier();
        courierDetails = getRandomCourierDataGenerator.createCourierWithRandomData();
        courierActions.createCourier(courierDetails);
        courierLoginCredentials = RegisterCourier.from(courierDetails);
        courierAssertions = new AddCourier();
    }

    @After
    @Step("Удаление зарегистрированного курьера")
    public void cleanData() {
        courierActions.courierDelete(createdCourierId);
    }

    @DisplayName("Тест на корректно создание курьера")
    @Description("Авторизация с правильными учетными записями")
    @Test
    public void shouldAllowSuccessfulLoginWithValidCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(courierLoginCredentials);
        createdCourierId = courierLogin.extract().path("id");
        courierAssertions.successLoginCourierAndTakeId(courierLogin);
    }

    @DisplayName("Тест на неудачную авторизацию курьера без учетных данных")
    @Description("Попытка авторизации с пустыми учетными данными")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new RegisterCourier("", ""));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на вход с отсутствующими учетными записями")
    @Description("Попытка входа с отсутствующими данными")
    @Test
    public void shouldReturnErrorWhenLoginWithNonExistentCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new RegisterCourier("cd", "fg"));
        courierAssertions.errorLoginCourierWithNotValidCredintals(courierLogin);
    }

    @DisplayName("Тест на ошибку входа курьера с незаполненным логином")
    @Description("Аутентификация без логина")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyUsername() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new RegisterCourier("", courierDetails.getPassword()));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку входа курьера с пустым паролем")
    @Description("Вход с отсутствующим паролем")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyPassword() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new RegisterCourier(courierDetails.getLogin(), ""));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }
}