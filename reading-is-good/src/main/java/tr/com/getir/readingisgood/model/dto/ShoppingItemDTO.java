package tr.com.getir.readingisgood.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tr.com.getir.readingisgood.model.ShoppingItem;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class ShoppingItemDTO {
    private Long itemId;
    private String itemName;
    private int itemAmount;
    private Date purchaseDate;

    public ShoppingItemDTO(ShoppingItem item) {
        itemName = item.getBook().getBookName();
        itemId = item.getBook().getId();
        itemAmount = item.getAmount();
        purchaseDate = item.getCreationDate();
    }
}
