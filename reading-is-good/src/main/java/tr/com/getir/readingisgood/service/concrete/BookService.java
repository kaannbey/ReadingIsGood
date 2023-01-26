package tr.com.getir.readingisgood.service.concrete;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.repository.IBookRepository;
import tr.com.getir.readingisgood.service.interfaces.IBookService;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class BookService implements IBookService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    @Autowired
    private IBookRepository repository;


    @Override
    @Transactional
    public Book addBook(BookDTO bookDTO) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Book book = repository.save(new Book(bookDTO));
            logger.info("New book added by user: " + userDetails.getUsername() + " : " + book);
            return book;
        } catch (DataIntegrityViolationException e) {
            String err = ErrorConstants.BOOK_HAS_ALREADY_BEEN_INSERTED;
            logger.error("Error occurred while book insertion by user:" + userDetails.getUsername() + " data: " + bookDTO + " error:" + err);
            throw new CustomException(err, Response.SC_BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public Book updateBook(Long id, int stock) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            if (stock < 0) {
                String err = FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO;
                logger.error("Error occurred while updating book by user:" + userDetails.getUsername() + " error:" + err);
                throw new CustomException(err, Response.SC_BAD_REQUEST);
            }
            Optional<Book> foundBook = repository.findById(id);
            if (foundBook.isEmpty()) {
                String err = ErrorConstants.BOOK_NOT_FOUND;
                logger.error("Error occurred while updating book by user:" + userDetails.getUsername() + " error:" + err);
                throw new CustomException(err, Response.SC_NOT_FOUND);
            }
            Book entity = foundBook.get();
            entity.setStock(stock);
            Book book = repository.save(entity);
            logger.info("Book updated by user: " + userDetails.getUsername() + " " + book);
            return book;
        } catch (OptimisticLockException e) {
            String err = ErrorConstants.INTERNAL_SERVER_ERROR;
            logger.error("Book couldn't be updated by user: " + userDetails.getUsername() + " bookId:" + id+ " error:" + err);
            throw new CustomException(err, Response.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public List<Book> retrieveBooks(int limit, int offset) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Book> books = repository.retrieveAll(limit, offset);
        logger.info("Books retrieved by user: " + userDetails.getUsername());
        return books;
    }

    @Override
    @Transactional
    public Book retrieveBook(Long id) {
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isEmpty()) {
            String err = ErrorConstants.BOOK_NOT_FOUND;
            logger.error("Error occurred while retrieving book by user: " + userDetails.getUsername() + " with book id: " + id + " error: " + err);
            throw new CustomException(err, Response.SC_NOT_FOUND);
        }
        logger.info("Book retrieved by user: " + userDetails.getUsername() + " with book id: " + id);
        return optionalBook.get();
    }
}
