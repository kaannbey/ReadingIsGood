package tr.com.getir.readingisgood.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tr.com.getir.readingisgood.model.AuthUser;

public interface IUserService extends UserDetailsService {
    /**
     * Loads user from db by username
     *
     * @param username username
     * @return user
     * @throws UsernameNotFoundException usernameNotFound
     */
    AuthUser findUserByName(String username) throws UsernameNotFoundException;
}
