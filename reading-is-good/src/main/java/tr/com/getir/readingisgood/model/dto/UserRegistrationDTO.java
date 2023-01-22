package tr.com.getir.readingisgood.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.enums.ERole;
import tr.com.getir.readingisgood.model.AuthUser;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotBlank(message = FieldErrorConstants.USER_NAME_CANT_BE_NULL)
    private String name;
    @NotBlank(message = FieldErrorConstants.USER_SURNAME_CANT_BE_NULL)
    private String surname;
    @NotBlank(message = FieldErrorConstants.USER_SURNAME_CANT_BE_NULL)
    private String username;
    @NotBlank(message = FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL)
    private String password;
    @NotBlank(message = FieldErrorConstants.USER_USERNAME_CANT_BE_NULL)
    private String email;

    /**
     * Builds user
     *
     * @param registrationDTO registration info for user
     * @return user
     */
    public AuthUser buildUser(UserRegistrationDTO registrationDTO) {
        return new AuthUser(registrationDTO.getName(), registrationDTO.getSurname(), registrationDTO.getUsername(), registrationDTO.getPassword(), registrationDTO.getEmail(), new HashSet<>(List.of(ERole.USER)));
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
