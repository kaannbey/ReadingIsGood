package tr.com.getir.readingisgood.service.interfaces;


import jakarta.validation.Valid;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.StatisticDTO;

import java.util.List;

public interface IOrderService {

    /**
     * Add book to order. (Buying books)
     *
     * @param bookId bookId
     * @param amount amount
     * @return orderDTO
     */
    OrderDTO buyBook(Long bookId, int amount);


    /**
     * Gives an order
     *
     * @return Order dto
     */
    OrderDTO approveOrder();

    /**
     * Retrieves order of auth user
     *
     * @param paginationDTO contains pagination info
     * @return List of order dto
     */
    List<OrderDTO> retrieveAllOrders(@Valid PaginationDTO paginationDTO);

    /**
     * Retrieves auth user orders
     *
     * @param paginationDTO contains pagination info
     * @return List of order dto
     */
    List<OrderDTO> retrieveAuthUserOrders(@Valid PaginationDTO paginationDTO);

    /**
     * Retrieves Auth user order by orderId
     *
     * @param orderId orderId
     * @return orderId
     */
    OrderDTO retrieveAuthUserOrderByOrderId(Long orderId);

    /**
     * Retrieves order by id. This method available for internal services or admin operations.
     *
     * @param orderId orderId
     * @return order
     */
    OrderDTO retrieveOrderById(Long orderId);

    /**
     * Retrieves user statistics
     *
     * @param userId userId
     * @return statistics
     */
    List<StatisticDTO> retrieveStatistics(Long userId);

}
