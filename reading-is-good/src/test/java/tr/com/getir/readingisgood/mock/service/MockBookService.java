package tr.com.getir.readingisgood.mock.service;

import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.service.interfaces.IBookService;

import java.util.List;

public class MockBookService implements IBookService {
    @Override
    public Book addBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public Book updateBook(Long id, int stock) {
        return null;
    }

    @Override
    public List<Book> retrieveBooks(int limit, int offset) {
        return null;
    }

    @Override
    public Book retrieveBook(Long id) {
        return null;
    }
}
