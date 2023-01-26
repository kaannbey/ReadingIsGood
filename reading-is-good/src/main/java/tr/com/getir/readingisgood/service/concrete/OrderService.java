package tr.com.getir.readingisgood.service.concrete;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.enums.OrderStatus;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.filter.AuthTokenFilter;
import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.Order;
import tr.com.getir.readingisgood.model.ShoppingItem;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.StatisticDTO;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IOrderRepository;
import tr.com.getir.readingisgood.service.interfaces.IBookService;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;
import tr.com.getir.readingisgood.service.interfaces.IUserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderRepository repository;
    @Autowired
    private IBookService bookService;

    @Override
    @Transactional
    public OrderDTO buyBook(Long bookId, int amount) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Order order = retrieveOrCreateAndRetrieveShoppingCartOrder(userDetails);
            Book book = bookService.retrieveBook(bookId);
            if (book.getStock() < amount) {
                String err = ErrorConstants.INSUFFICIENT_BOOK_STOCK;
                logger.error("Error occurred while buying book by user: " + userDetails.getUsername() + " bookId:" + bookId + " amount:" + amount + " stock: " + book.getStock() + " error: " + err);
                throw new CustomException(err, Response.SC_BAD_REQUEST);
            }
            book.setStock(book.getStock() - amount);
            Optional<ShoppingItem> foundItem = order.getItems().stream().filter(item -> Objects.equals(item.getBook().getId(), bookId)).findFirst();
            ShoppingItem shoppingItem;
            if (foundItem.isEmpty()) {
                shoppingItem = new ShoppingItem(amount, book);
                order.addItem(shoppingItem);
                logger.debug("User purchased a new book user:" + userDetails.getUsername() + " item " + shoppingItem);
            } else {
                shoppingItem = foundItem.get();
                shoppingItem.setAmount(shoppingItem.getAmount() + amount);
                order.setItemAmount(order.getItemAmount() + amount);
                logger.debug("User purchased same book user:" + userDetails.getUsername() + " item " + shoppingItem);
            }
            repository.save(order);
            logger.info("Book is purchased by user:" + userDetails.getUsername() + " book: " + book + " amount: " + amount);
            return OrderDTO.build(order);
        }
        catch (OptimisticLockException e) {
            String err = ErrorConstants.INTERNAL_SERVER_ERROR;
            logger.error("Book couldn't be bought by user: " + userDetails.getUsername() + " bookId:" + bookId+ " error:" + err);
            throw new CustomException(err, Response.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public OrderDTO approveOrder() {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = repository.findByUserIdAndOrderStatus(userDetails.getId(), OrderStatus.ON_SHOPPING_CART);
        if (order == null) {
            String err = ErrorConstants.ORDER_CANT_BE_NULL;
            logger.error("Order can't approve by user:" + userDetails.getUsername() + " error: " + err);
            throw new CustomException(err, Response.SC_BAD_REQUEST);
        }
        if (order.getItems().size() == 0) {
            String err = ErrorConstants.ORDER_ITEMS_EMPTY;
            logger.error("Order can't approve by user:" + userDetails.getUsername() + " error: " + err);
            throw new CustomException(err, Response.SC_BAD_REQUEST);
        }
        order.approveOrder();
        return OrderDTO.build(order);
    }

    @Override
    @Transactional
    public List<OrderDTO> retrieveAllOrders(@Valid PaginationDTO paginationDTO) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDTO> orders = repository.findByPaginationAndTimeInterval(paginationDTO.getStartDate(), paginationDTO.getEndDate(), paginationDTO.getLimit(), paginationDTO.getOffset()).stream().map(OrderDTO::build).collect(Collectors.toList());
        logger.info("All orders retrieved by user: " + userDetails.getUsername());
        return orders;
    }

    @Override
    @Transactional
    public List<OrderDTO> retrieveAuthUserOrders(@Valid PaginationDTO paginationDTO) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDTO> orders = repository.findByPaginationTimeInternalAndUserId(userDetails.getId(), paginationDTO.getStartDate(), paginationDTO.getEndDate(), paginationDTO.getLimit(), paginationDTO.getOffset()).stream().map(OrderDTO::build).collect(Collectors.toList());
        logger.info("Auth user orders retrieved by user: " + userDetails.getUsername());
        return orders;
    }

    @Override
    @Transactional
    public OrderDTO retrieveAuthUserOrderByOrderId(Long orderId) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = repository.findByUserIdAndOrderId(orderId, userDetails.getId());
        if (order == null) {
            String err = ErrorConstants.ORDER_NOT_FOUND;
            logger.error("Order not found for user:" + userDetails.getUsername() + " orderId: " + orderId + " error: " + err);
            throw new CustomException(err, Response.SC_NOT_FOUND);
        }
        logger.info("Auth user orders retrieved by user: " + userDetails.getUsername() + " with orderId: " + orderId);
        return OrderDTO.build(order);
    }

    @Override
    @Transactional
    public OrderDTO retrieveOrderById(Long orderId) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Order order = repository.findByOrderId(orderId);
        if (order == null) {
            String err = ErrorConstants.ORDER_NOT_FOUND;
            logger.error("Order not found for user:" + userDetails.getUsername() + " orderId: " + orderId + " error: " + err);
            throw new CustomException(err, Response.SC_NOT_FOUND);
        }
        logger.debug("Order retrieved by user: " + userDetails.getUsername() + "with orderId: " + orderId);
        return OrderDTO.build(order);
    }

    @Override
    public List<StatisticDTO> retrieveStatistics(Long userId) {
        return StatisticDTO.build(repository.retrieveStatistics(userId));
    }

    /**
     * Retrieve order for user
     *
     * @param userDetails userDetails
     * @return shopping cart
     */
    @Transactional
    private Order retrieveOrCreateAndRetrieveShoppingCartOrder(AuthUserDetails userDetails) {
        Order order = repository.findByUserIdAndOrderStatus(userDetails.getId(), OrderStatus.ON_SHOPPING_CART);
        if (order == null) {
            AuthUser user = userService.findUserByName(userDetails.getUsername());
            order = Order.build(user);
            logger.debug("Order created for user:" + userDetails.getUsername() + " with id:" + order.getId());
        }
        return order;
    }
}
