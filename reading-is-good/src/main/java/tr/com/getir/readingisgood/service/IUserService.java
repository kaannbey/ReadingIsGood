package tr.com.getir.readingisgood.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;

public interface IUserService extends UserDetailsService {
    /**
     * Loads user from db by username
     * @param username username
     * @return user
     * @throws UsernameNotFoundException usernameNotFound
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
