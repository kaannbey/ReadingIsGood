package tr.com.getir.readingisgood.service.concrete;

import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tr.com.getir.readingisgood.constant.Constants;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IUserRepository;
import tr.com.getir.readingisgood.util.JwtUtils;
import tr.com.getir.readingisgood.utils.TestAuthUserUtil;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    IUserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    Authentication authentication;
    @Mock
    JwtUtils jwtUtils;
    TestAuthUserUtil testAuthUserUtil = new TestAuthUserUtil();

    private UserRegistrationDTO prepareUserRegistrationDTO() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setEmail("email");
        userRegistrationDTO.setName("name");
        userRegistrationDTO.setSurname("surname");
        userRegistrationDTO.setUsername("username");
        return userRegistrationDTO;
    }

    @Test
    public void registerSuccessTest() {
        UserRegistrationDTO dto = prepareUserRegistrationDTO();
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("");
        Assert.assertEquals(Constants.OK, authService.register(dto, false));
    }

    @Test
    public void registerDataIntegrityViolationFailureTest() {
        UserRegistrationDTO dto = prepareUserRegistrationDTO();
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenThrow(new DataIntegrityViolationException(""));
        try {
            authService.register(dto, false);
        } catch (CustomException e) {
            Assert.assertEquals(e.getMessage(), ErrorConstants.USERNAME_OR_EMAIL_IS_TAKEN);
            Assert.assertEquals(e.getStatus(), Response.SC_BAD_REQUEST);
        }
    }

    @Test
    public void registerUserPasswordFailureTest() {
        UserRegistrationDTO dto = prepareUserRegistrationDTO();
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenThrow(new IllegalArgumentException(""));
        try {
            authService.register(dto, false);
        } catch (CustomException e) {
            Assert.assertEquals(e.getMessage(), FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL);
            Assert.assertEquals(e.getStatus(), Response.SC_BAD_REQUEST);
        }
    }

    @Test
    public void loginAuthFailureTest() {
        UserRegistrationDTO dto = prepareUserRegistrationDTO();
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new InternalAuthenticationServiceException(""));
        try {
            Assert.assertEquals(authService.login(new UserLoginDTO()),"");
        } catch (CustomException e) {
            Assert.assertEquals(e.getMessage(), ErrorConstants.USERNAME_OR_PASSWORD_IS_INCORRECT);
            Assert.assertEquals(e.getStatus(), Response.SC_UNAUTHORIZED);
        }
    }

    @Test
    public void loginSuccessTest() {
        AuthUserDetails userDetails = testAuthUserUtil.getUserDetails();
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateJwtToken(Mockito.any())).thenReturn("");
        Assert.assertEquals(authService.login(new UserLoginDTO()),"");

    }

}