package tr.com.getir.readingisgood.service;

import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.dto.CustomerLoginDTO;
import tr.com.getir.readingisgood.dto.CustomerRegistrationDTO;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.Customer;
import tr.com.getir.readingisgood.repository.ICustomerRepository;

import java.util.List;

@Service
public class AuthService implements IAuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);
    @Autowired
    private ICustomerRepository repository;

    @Override
    public Customer register(CustomerRegistrationDTO registrationDTO) {
        try {
            return repository.save(Customer.buildCustomer(registrationDTO));
        } catch (Exception e) {
            logger.error("Error occurred when registering the customer:" + registrationDTO.toString());
            //todo user has already been
            throw new CustomException(e.getMessage(), Response.SC_BAD_GATEWAY);
        }
    }

    @Override
    public String login(CustomerLoginDTO loginDTO) {
        List<Customer> customerList = repository.findByLoginInfo(loginDTO.getUsername(), loginDTO.getPassword());
        if (customerList.size() > 0) {
            return generateToken(customerList.get(0));
        }
        logger.error("Error occurred when user try to login with username:" + loginDTO.getUsername());
        throw new CustomException(ErrorConstants.USERNAME_OR_PASSWORD_IS_INCORRECT, Response.SC_OK);
    }

    private String generateToken(Customer customer) {
        return "";
    }
}
