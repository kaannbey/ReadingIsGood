package tr.com.getir.readingisgood.service;

import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;

public interface IAuthService {
    /**
     * Registers new customer
     *
     * @param registrationDTO registration dto
     * @return authenticated user
     */
    AuthUser register(UserRegistrationDTO registrationDTO);

    /**
     * Logged in user
     *
     * @param loginDTO login dto
     * @return apiParam
     */
    String login(UserLoginDTO loginDTO);
}
