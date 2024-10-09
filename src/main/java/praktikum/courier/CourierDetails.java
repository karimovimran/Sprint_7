package praktikum.courier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierDetails {
    private String login;
    private String password;
    private String firstname;
}
