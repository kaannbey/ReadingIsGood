
package tr.com.getir.readingisgood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.model.infra.AEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Book")
public class Book extends AEntity {
    @NotNull(message = FieldErrorConstants.BOOK_NAME_CANT_BE_NULL_NOR_EMPTY)
    @Column(name = "book_name", unique = true)
    private String bookName;
    @Min(message = FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO, value = 0)
    @Column(name = "stock")
    private int stock;
}
