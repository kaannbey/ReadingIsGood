package tr.com.getir.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IUserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AuthUserDetails.build(repository.findByUsername(username));
    }
}
