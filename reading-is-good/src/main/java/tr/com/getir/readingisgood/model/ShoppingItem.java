package tr.com.getir.readingisgood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.model.infra.AEntity;

@Entity
@Table(name = "shopping_item")
@Getter
@Setter
@NoArgsConstructor
public class ShoppingItem extends AEntity {
    @Min(message = FieldErrorConstants.BOOK_AMOUNT_CANT_BE_LESS_THAN_ONE, value = 0)
    @Column(name = "number")
    private int amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public ShoppingItem(int amount, Book book) {
        this.amount = amount;
        this.book = book;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "amount=" + amount +
                ", book=" + book +
                '}';
    }
}
