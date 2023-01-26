package tr.com.getir.readingisgood.service.concrete;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.Constants;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.filter.AuthTokenFilter;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.service.interfaces.IAdminService;
import tr.com.getir.readingisgood.service.interfaces.IAuthService;
import tr.com.getir.readingisgood.service.interfaces.IBookService;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;

import java.util.List;
import java.util.Objects;

@Service
@Setter
public class AdminService implements IAdminService {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private IAuthService authService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private IOrderService orderService;

    @Value("${" + Constants.ADMIN_SECRET + "}")
    private String adminHeader;
    @Value("${" + Constants.DEV_MODE + "}")
    private boolean isDevelopment;

    @Override
    @Transactional
    public String register(UserRegistrationDTO registrationDTO, String adminHeader) {
        if (!isDevelopment || !Objects.equals(adminHeader, this.adminHeader)) {
            logger.error("Someone tries to register as admin. User info: " + registrationDTO);
            throw new CustomException(ErrorConstants.INTERNAL_SERVER_ERROR, Response.SC_INTERNAL_SERVER_ERROR);
        }
        return authService.register(registrationDTO, true);
    }

    @Override
    @Transactional
    public Book addBook(BookDTO bookDTO) {
        return bookService.addBook(bookDTO);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, int stock) {
        return bookService.updateBook(id, stock);
    }

    @Override
    public OrderDTO retrieveOrderById(Long orderId) {
        return orderService.retrieveOrderById(orderId);
    }

    @Override
    public List<OrderDTO> retrieveAllOrders(PaginationDTO paginationDTO) {
        return orderService.retrieveAllOrders(paginationDTO);
    }
}
