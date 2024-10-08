package praktikum.courier;

import io.qameta.allure.Step;
import net.datafaker.Faker;

public class GetRandomCourier {
    static Faker faker = new Faker();

    @Step("Создание курьера со случайными данными")
    public CourierDetails createCourierWithRandomData() {
        return new CourierDetails(
                faker.name().name(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
}