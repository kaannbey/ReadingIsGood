package tr.com.getir.readingisgood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.getir.readingisgood.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {
    /**
     * Finds book by name
     *
     * @param bookName book name
     * @return book
     */
    Optional<Book> findByBookName(String bookName);

    /**
     * Retrieves Books
     *
     * @return books
     */
    @Query("SELECT b FROM Book b ORDER BY id LIMIT ?1 OFFSET ?2")
    List<Book> retrieveAll(int limit, int offset);
}
