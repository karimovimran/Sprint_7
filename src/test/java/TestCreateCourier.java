import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.*;

public class TestCreateCourier {
    int courierId;
    protected GetRandomCourier courierGenerateRandomData = new GetRandomCourier();
    private CourierDetails courierInfo;
    protected HandleCourier courierSteps;
    private AddCourier courierAssertVoid;
    private RegisterCourier courierLoginCredintals;

    @Before
    @Step("Создание образцов данных для курьера")
    public void setUp() {
        courierSteps = new HandleCourier();
        courierInfo = courierGenerateRandomData.createCourierWithRandomData();
        courierSteps.createCourier(courierInfo);
        courierLoginCredintals = RegisterCourier.from(courierInfo);
        courierAssertVoid = new AddCourier();
    }

    @After
    @Step("Удаление созданного профиля курьера")
    public void cleanData() {
        courierSteps.courierDelete(courierId);
    }

    @DisplayName("Тест успешного создания курьера")
    @Description("Логин с действительными данными")
    @Test
    public void testSuccessCourierLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(courierLoginCredintals);
        courierId = courierLogin.extract().path("id");
        courierAssertVoid.successLoginCourierAndTakeId(courierLogin);
    }

    @DisplayName("Тест на неудачную авторизацию курьера без учетных данных")
    @Description("Вход с пустыми данными")
    @Test
    public void testErrorCourierLoginWithEmptyCreds() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new RegisterCourier("", ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на неудачную авторизацию с несуществующими учетными данными")
    @Description("Вход с несуществующими учетными данными")
    @Test
    public void testErrorCourierLoginWithDoesNotExistCredintals() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new RegisterCourier("a", "b"));
        courierAssertVoid.errorLoginCourierWithNotValidCredintals(courierLogin);
    }

    @DisplayName("Тест на вход курьера без логина")
    @Description("Авторизация без логина")
    @Test
    public void testErrorLoginCourierWithEmptyLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new RegisterCourier("", courierInfo.getPassword()));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации при пустом пароле")
    @Description("Вход без пароля")
    @Test
    public void testErrorLoginCourierWithEmptyPassword() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new RegisterCourier(courierInfo.getLogin(), ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

}
