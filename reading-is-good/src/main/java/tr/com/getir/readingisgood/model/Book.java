
package tr.com.getir.readingisgood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.infra.AEntity;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book extends AEntity {

    @NotNull(message = FieldErrorConstants.BOOK_NAME_CANT_BE_NULL_NOR_EMPTY)
    @Column(name = "book_name", unique = true)
    private String bookName;

    @Min(message = FieldErrorConstants.BOOK_ID_CANT_BE_NULL, value = 0)
    @Column(name = "stock")
    private int stock;

    @Version
    private int version;


    public Book(BookDTO dto) {
        this.bookName = dto.getBookName();
        this.stock = dto.getStock();
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", stock=" + stock +
                '}';
    }
}
