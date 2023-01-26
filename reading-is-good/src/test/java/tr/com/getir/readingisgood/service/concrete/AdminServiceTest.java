package tr.com.getir.readingisgood.service.concrete;

import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import tr.com.getir.readingisgood.constant.Constants;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.mock.service.MockAuthService;
import tr.com.getir.readingisgood.mock.service.MockBookService;
import tr.com.getir.readingisgood.mock.service.MockOrderService;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.service.interfaces.IAuthService;
import tr.com.getir.readingisgood.service.interfaces.IBookService;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class AdminServiceTest {
    private final AdminService adminService = new AdminService();

    private final IAuthService authService = new MockAuthService();
    private final IBookService bookService = new MockBookService();
    private final IOrderService orderService = new MockOrderService();

    @Value("${" + Constants.ADMIN_SECRET + "}")
    public String adminHeader;

    @Test
    public void registerNoAdminHeaderFailureTest() {
        adminService.setAuthService(authService);
        adminService.setDevelopment(true);
        try {
            adminService.register(new UserRegistrationDTO(), "");
            Assert.fail();
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_INTERNAL_SERVER_ERROR);
            Assert.assertEquals(e.getMessage(), ErrorConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void registerDevModeFailureTest() {
        adminService.setAuthService(authService);
        adminService.setDevelopment(false);
        try {
            adminService.register(new UserRegistrationDTO(), adminHeader);
            Assert.fail();
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_INTERNAL_SERVER_ERROR);
            Assert.assertEquals(e.getMessage(), ErrorConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void registerTestDevSuccessfullTest() {
        adminService.setAuthService(authService);
        adminService.setDevelopment(true);
        Assert.assertNull(adminService.register(new UserRegistrationDTO(), adminHeader));
    }

    @Test
    public void addBookTest() {
        adminService.setBookService(bookService);
        Assert.assertNull(adminService.addBook(new BookDTO()));
    }

    @Test
    public void updateBookTest() {
        adminService.setBookService(bookService);
        Assert.assertNull(adminService.updateBook(null, 0));
    }

    @Test
    public void retrieveOrderByIdTest() {
        adminService.setOrderService(orderService);
        Assert.assertNull(adminService.retrieveOrderById( null));
    }

    @Test
    public void retrieveAllOrdersTest() {
        adminService.setOrderService(orderService);
        Assert.assertNull(adminService.retrieveAllOrders( null));
    }


}