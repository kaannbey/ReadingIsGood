package tr.com.getir.readingisgood.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = FieldErrorConstants.USER_USERNAME_CANT_BE_NULL)
    private String username;
    @NotBlank(message = FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL)
    private String password;
}
