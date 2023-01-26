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
import org.springframework.context.annotation.PropertySource;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.enums.OrderStatus;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.Order;
import tr.com.getir.readingisgood.model.ShoppingItem;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.repository.IOrderRepository;
import tr.com.getir.readingisgood.service.interfaces.IBookService;
import tr.com.getir.readingisgood.service.interfaces.IUserService;
import tr.com.getir.readingisgood.utils.TestAuthUserUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class OrderServiceTest {

    @Mock
    IOrderRepository orderRepository;
    @Mock
    IBookService bookService;
    @Mock
    IUserService userService;
    @InjectMocks
    OrderService orderService;
    private final TestAuthUserUtil testAuthUserUtil = new TestAuthUserUtil();

    @Before
    public void init() {
        testAuthUserUtil.mockAuthenticatedUser();
    }

    public Book getBook() {
        Book book = new Book();
        book.setStock(25);
        book.setBookName("test");
        book.setId(5L);
        return book;
    }

    @Test
    public void buyBookBookStockFailureTest() {
        //item creation
        Book book = getBook();
        //Mockings
        Mockito.when(bookService.retrieveBook(Mockito.anyLong())).thenReturn(book);
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.anyLong(), Mockito.any(OrderStatus.class))).thenReturn(null);
        Mockito.when(userService.findUserByName(Mockito.any())).thenReturn(new AuthUser());
        try {
            orderService.buyBook(3L, 50);
        } catch (CustomException e) {
            Assert.assertEquals(e.getMessage(), ErrorConstants.INSUFFICIENT_BOOK_STOCK);
            Assert.assertEquals(e.getStatus(), Response.SC_BAD_REQUEST);
        }
    }

    @Test
    public void buyBookBookShoppingItemEmptySuccessTest() {
        //item creation
        Book book = getBook();
        Order order = new Order();
        //Mockings
        Mockito.when(bookService.retrieveBook(Mockito.anyLong())).thenReturn(book);
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.anyLong(), Mockito.any(OrderStatus.class))).thenReturn(order);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        OrderDTO response = orderService.buyBook(3L, 1);
        //check
        Assert.assertEquals(OrderDTO.build(order), response);
        Assert.assertEquals(response.getItemAmount(), 1);
        Assert.assertEquals(response.getItems().size(), 1);
    }

    @Test
    public void buyBookShoppingItemNotEmptySuccessTest() {
        //item creation
        Book book = getBook();
        Order order = new Order();
        ShoppingItem boughtItem = new ShoppingItem(1, book);
        order.getItems().add(boughtItem);

        //Mockings
        Mockito.when(bookService.retrieveBook(Mockito.anyLong())).thenReturn(book);
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.anyLong(), Mockito.any(OrderStatus.class))).thenReturn(order);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        //method call
        OrderDTO response = orderService.buyBook(5L, 1);
        //check
        Assert.assertEquals(OrderDTO.build(order), response);
        Assert.assertEquals(response.getItems().size(), 1);

    }

    @Test
    public void retrieveAuthUserOrdersSuccessTest() {
        //Prepare data
        List<Order> expectedResponse = Collections.emptyList();
        PaginationDTO paginationDTO = new PaginationDTO(new Date(), new Date(), 0, 0);
        //Mocking
        Mockito.when(orderRepository.findByPaginationTimeInternalAndUserId(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(expectedResponse);
        //check
        List<OrderDTO> response = orderService.retrieveAuthUserOrders(paginationDTO);
        Assert.assertEquals(response.size(), 0);
    }

    @Test
    public void retrieveAllOrdersSuccessTest() {
        //Prepare data
        List<Order> expectedResponse = Collections.emptyList();
        PaginationDTO paginationDTO = new PaginationDTO(new Date(), new Date(), 0, 0);
        //check
        List<OrderDTO> response = orderService.retrieveAllOrders(paginationDTO);
        Assert.assertEquals(response.size(), 0);
    }

    @Test
    public void retrieveAuthUserOrderByOrderIdFailTest() {
        Order order = new Order();
        try{
            Assert.assertEquals(OrderDTO.build(order), orderService.retrieveAuthUserOrderByOrderId(null));
        }
        catch (CustomException e){
            Assert.assertEquals(e.getMessage(),ErrorConstants.ORDER_NOT_FOUND);
            Assert.assertEquals(e.getStatus(),Response.SC_NOT_FOUND);
        }
    }

    @Test
    public void retrieveOrderByIdFailureTest() {
        Order order = new Order();
        try{
            Assert.assertEquals(OrderDTO.build(order), orderService.retrieveOrderById(null));
        }
        catch (CustomException e){
            Assert.assertEquals(e.getMessage(),ErrorConstants.ORDER_NOT_FOUND);
            Assert.assertEquals(e.getStatus(),Response.SC_NOT_FOUND);
        }
    }

    @Test
    public void approveOrderSuccessTest(){
        Order order = new Order();
        ShoppingItem shoppingItem = new ShoppingItem(3, new Book());
        order.setItems(new HashSet<>(List.of(shoppingItem)));
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.any(),Mockito.any())).thenReturn(order);
        Assert.assertEquals(OrderDTO.build(order).getItems(),orderService.approveOrder().getItems());
    }

    @Test
    public void approveOrderNullFailureTest(){
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.any(),Mockito.any())).thenReturn(null);
        try{
            orderService.approveOrder();
        }
        catch (CustomException e){
            Assert.assertEquals(e.getMessage(),ErrorConstants.ORDER_CANT_BE_NULL);
            Assert.assertEquals(e.getStatus(),Response.SC_BAD_REQUEST);
        }
    }

    @Test
    public void approveOrderOrderItemsEmptyFailureTest(){
        Order order = new Order();
        Mockito.when(orderRepository.findByUserIdAndOrderStatus(Mockito.any(),Mockito.any())).thenReturn(order);
        try{
            orderService.approveOrder();
        }
        catch (CustomException e){
            Assert.assertEquals(e.getMessage(),ErrorConstants.ORDER_ITEMS_EMPTY);
            Assert.assertEquals(e.getStatus(),Response.SC_BAD_REQUEST);
        }
    }


}