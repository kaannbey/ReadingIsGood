package tr.com.getir.readingisgood.service.concrete;

import jakarta.transaction.Transactional;
import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.Constants;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IUserRepository;
import tr.com.getir.readingisgood.service.interfaces.IAuthService;
import tr.com.getir.readingisgood.util.JwtUtils;

@Service
public class AuthService implements IAuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String register(UserRegistrationDTO registrationDTO, boolean isAdmin) {
        try {
            registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            AuthUser entity = registrationDTO.buildUser(registrationDTO, isAdmin);
            userRepository.save(entity);
            logger.info("User registered:" + entity);
            return Constants.OK;
        } catch (DataIntegrityViolationException e) {
            String err = ErrorConstants.USERNAME_OR_EMAIL_IS_TAKEN;
            logger.error("Error occurred while registering the user:" + registrationDTO + " error:" + err);
            throw new CustomException(err, Response.SC_BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            String err = FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL;
            logger.error("Error occurred while registering the user:" + registrationDTO + " error:" + err);
            throw new CustomException(err, Response.SC_BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public String login(UserLoginDTO loginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (InternalAuthenticationServiceException e) {
            logger.error("User Login attempt fail with username: " + loginDTO.getUsername());
            throw new CustomException(ErrorConstants.USERNAME_OR_PASSWORD_IS_INCORRECT, Response.SC_UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        logger.info("User logged in with username: " + userDetails.getUsername() + " roles" + userDetails.getAuthorities());

        return jwtUtils.generateJwtToken(authentication);
    }
}
