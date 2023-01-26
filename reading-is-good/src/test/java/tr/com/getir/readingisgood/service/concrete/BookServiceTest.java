package tr.com.getir.readingisgood.service.concrete;

import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.repository.IBookRepository;
import tr.com.getir.readingisgood.utils.TestAuthUserUtil;

import java.util.ArrayList;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class BookServiceTest {

    private final BookService bookService = new BookService();

    @Mock
    IBookRepository bookRepository;

    @Before
    public void init() {
        new TestAuthUserUtil().mockAuthenticatedUser();
    }


    @Test
    public void addBookSuccessTest() {
        //prepare data
        BookDTO bookDTO = new BookDTO(3, "test");
        Book savedBook = new Book(bookDTO);
        //mocking and settings
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(savedBook);
        bookService.setRepository(bookRepository);
        Book expectedResponse = bookService.addBook(bookDTO);
        //check
        Assert.assertEquals(savedBook, expectedResponse);
    }

    @Test
    public void addBookFailTest() {
        //prepare data
        BookDTO bookDTO = new BookDTO(3, "test");
        //Mocking
        Mockito.when(bookRepository.save(Mockito.any())).thenThrow(new DataIntegrityViolationException(""));
        bookService.setRepository(bookRepository);
        //check
        try {
            bookService.addBook(bookDTO);
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_BAD_REQUEST);
            Assert.assertEquals(e.getMessage(), ErrorConstants.BOOK_HAS_ALREADY_BEEN_INSERTED);
        }
    }

    @Test
    public void retrieveBooksSuccessTest() {
        //prepare data
        ArrayList<Book> expectedResponse = new ArrayList<>();
        //mocking
        Mockito.when(bookRepository.retrieveAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expectedResponse);
        bookService.setRepository(bookRepository);
        //check
        Assert.assertEquals(bookService.retrieveBooks(3, 5), expectedResponse);
    }

    @Test
    public void retrieveBookErrorTest() {
        //mocking
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        bookService.setRepository(bookRepository);
        //check
        try {
            bookService.retrieveBook(1L);
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_NOT_FOUND);
            Assert.assertEquals(e.getMessage(), ErrorConstants.BOOK_NOT_FOUND);
        }
    }

    @Test
    public void retrieveBookSuccessTest() {
        //prepare data
        Book expectedResponse = new Book();
        expectedResponse.setBookName("test");
        expectedResponse.setStock(3);
        //Mocking
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedResponse));
        bookService.setRepository(bookRepository);
        //Checking
        Assert.assertEquals(bookService.retrieveBook(1L), expectedResponse);
    }


    @Test
    public void updateBookStockFailureTest() {
        try {
            bookService.updateBook(1L, -1);
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_BAD_REQUEST);
            Assert.assertEquals(e.getMessage(), FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO);
        }
    }

    @Test
    public void updateBookNotFoundFailureTest() {
        //Mocking
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        bookService.setRepository(bookRepository);
        //checking
        try {
            bookService.updateBook(1L, 3);
        } catch (CustomException e) {
            Assert.assertEquals(e.getStatus(), Response.SC_NOT_FOUND);
            Assert.assertEquals(e.getMessage(), ErrorConstants.BOOK_NOT_FOUND);
        }
    }

    @Test
    public void updateBookSuccessTest() {
        //preparing data
        BookDTO test = new BookDTO(3, "test");
        Book expectedResponse = new Book(test);
        //mocking and settings
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(expectedResponse);
        bookService.setRepository(bookRepository);
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedResponse));
        bookService.setRepository(bookRepository);
        //checking
        Assert.assertEquals(bookService.updateBook(1L, 3), expectedResponse);
    }
}