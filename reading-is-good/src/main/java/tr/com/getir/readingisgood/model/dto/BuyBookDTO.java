package tr.com.getir.readingisgood.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;

@Getter
@Setter
public class BuyBookDTO {
    @NotNull(message = FieldErrorConstants.BOOK_ID_CANT_BE_NULL)
    private Long bookId;
    @Min(message = FieldErrorConstants.BOOK_AMOUNT_CANT_BE_LESS_THAN_ONE, value = 1)
    private int amount;
}
