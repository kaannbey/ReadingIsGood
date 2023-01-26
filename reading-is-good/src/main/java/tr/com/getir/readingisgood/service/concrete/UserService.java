package tr.com.getir.readingisgood.service.concrete;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.filter.AuthTokenFilter;
import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IUserRepository;
import tr.com.getir.readingisgood.service.interfaces.IUserService;

@Service
@Setter
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private IUserRepository repository;

    @Override
    @Transactional
    public AuthUser findUserByName(String username) throws UsernameNotFoundException {
        AuthUser user = repository.findByUsername(username);
        if (user == null) {
            String err = ErrorConstants.USER_NOT_FOUND;
            logger.error("Error occurred while loading user by name user: " + username + " error: " + err);
            throw new CustomException(err, Response.SC_NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AuthUserDetails.build(findUserByName(username));
    }
}
