package tr.com.getir.readingisgood.service;

import tr.com.getir.readingisgood.dto.CustomerLoginDTO;
import tr.com.getir.readingisgood.dto.CustomerRegistrationDTO;
import tr.com.getir.readingisgood.model.Customer;

public interface IAuthService {
    Customer register(CustomerRegistrationDTO registrationDTO);

    String login(CustomerLoginDTO loginDTO);
}
