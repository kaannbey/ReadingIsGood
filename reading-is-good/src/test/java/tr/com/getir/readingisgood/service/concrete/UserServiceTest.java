package tr.com.getir.readingisgood.service.concrete;

import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.mock.repository.MockUserRepository;
import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IUserRepository;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class UserServiceTest {

    @InjectMocks
    private UserService userService ;
    @Mock
    private IUserRepository userRepository ;

    @Test
    public void findUserByNameFailTest() {
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
        try {
            userService.findUserByName("");
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_NOT_FOUND);
            Assert.assertEquals(e.getMessage(), ErrorConstants.USER_NOT_FOUND);
        }
    }

    @Test
    public void findUserByNameSuccessTest() {
        AuthUser expectedResponse = new AuthUser();
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(expectedResponse);
        Assert.assertEquals(userService.findUserByName(""),expectedResponse);
    }

    @Test
    public void loadByUsernameSuccessTest() {
        AuthUser authUser = new AuthUser();
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(authUser);
        AuthUserDetails expectedResponse = AuthUserDetails.build(authUser);
        Assert.assertEquals(userService.loadUserByUsername(""), expectedResponse);
    }

}