package tr.com.getir.readingisgood.utils;

import lombok.Getter;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.com.getir.readingisgood.enums.ERole;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;

import java.util.List;


public class TestAuthUserUtil {

    @Mock
    SecurityContextHolder securityContextHolder;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;
    @Getter
    AuthUserDetails userDetails = new AuthUserDetails(1l, "test", "email", "password", List.of(new SimpleGrantedAuthority(ERole.ROLE_USER.value())));

    public void mockAuthenticatedUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    }
}
