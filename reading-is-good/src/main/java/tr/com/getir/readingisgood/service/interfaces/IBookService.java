package tr.com.getir.readingisgood.service.interfaces;

import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;

import java.util.List;

public interface IBookService {

    /**
     * Inserts new book
     *
     * @param bookDTO book info
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
     * Retrieve books with pagination
     *
     * @param limit  limit
     * @param offset pageNumber
     * @return book
     */
    List<Book> retrieveBooks(int limit, int offset);

    /**
     * Retrieve book
     *
     * @param id bookId
     * @return book
     */
    Book retrieveBook(Long id);
}
