package tr.com.getir.readingisgood.service.interfaces;

import jakarta.validation.Valid;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;

import java.util.List;

public interface IAdminService {
    /**
     * Registers new admin
     *
     * @param registrationDTO contains registration info
     * @param adminHeader     adminHeader
     * @return ok message
     */
    String register(UserRegistrationDTO registrationDTO, String adminHeader);

    /**
     * Inserts new book
     *
     * @param bookDTO contains book info
     * @return book
     */
    Book addBook(BookDTO bookDTO);

    /**
     * Updates book by id
     *
     * @param id    book id
     * @param stock stock
     * @return book
     */
    Book updateBook(Long id, int stock);

    /**
     * Retrieves order by id.
     *
     * @param orderId orderId
     * @return order
     */
    OrderDTO retrieveOrderById(Long orderId);

    /**
     * Retrieves order of auth user
     *
     * @param paginationDTO contains pagination info
     * @return List of order dto
     */
    List<OrderDTO> retrieveAllOrders(@Valid PaginationDTO paginationDTO);
}
