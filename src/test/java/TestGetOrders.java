import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import praktikum.order.OrderAssert;
import praktikum.order.OrderPhase;

public class TestGetOrders {
    private OrderPhase orderSteps;
    private OrderAssert orderAssertVoid = new OrderAssert();

    @Before
    public void setUp(){
        orderSteps = new OrderPhase();
    }

    @DisplayName("Верификация списка заказов")
    @Description("Верификация успешного получения списка заказов")
    @Test
    public void testGetOrdersList(){
        ValidatableResponse response = orderSteps.getOrdersList();
        orderAssertVoid.successGetOrdersList(response);
    }
}