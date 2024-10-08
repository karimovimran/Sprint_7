package praktikum.courier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterCourier {
    private String login;
    private String password;

    public static RegisterCourier from(CourierDetails courierInfo) {
        return new RegisterCourier(courierInfo.getLogin(), courierInfo.getPassword());
    }
}
