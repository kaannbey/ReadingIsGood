package tr.com.getir.readingisgood.service.interfaces;

import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;

public interface IAuthService {
    /**
     * Registers new user
     *
     * @param registrationDTO contains registration info
     * @param isAdmin         specifies user whether admin or not
     * @return ok message
     */
    String register(UserRegistrationDTO registrationDTO, boolean isAdmin);

    /**
     * Logged in user
     *
     * @param loginDTO contains login info
     * @return apiParam
     */
    String login(UserLoginDTO loginDTO);
}
