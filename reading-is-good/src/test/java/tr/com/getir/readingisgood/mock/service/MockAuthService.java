package tr.com.getir.readingisgood.mock.service;

import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.service.interfaces.IAuthService;

public class MockAuthService implements IAuthService {
    @Override
    public String register(UserRegistrationDTO registrationDTO, boolean isAdmin) {
        return null;
    }

    @Override
    public String login(UserLoginDTO loginDTO) {
        return null;
    }
}
