package tr.com.getir.readingisgood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class CustomerRegistrationDTO {
    @NotBlank(message = FieldErrorConstants.CUSTOMER_NAME_CANT_BE_NULL)
    private String name;
    @NotBlank(message = FieldErrorConstants.CUSTOMER_SURNAME_CANT_BE_NULL)
    private String surname;
    @NotBlank(message = FieldErrorConstants.CUSTOMER_SURNAME_CANT_BE_NULL)
    private String username;
    @NotBlank(message = FieldErrorConstants.CUSTOMER_PASSWORD_CANT_BE_NULL)
    private String password;

    @Override
    public String toString() {
        return "CustomerRegistrationDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
