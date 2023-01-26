package tr.com.getir.readingisgood.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {
    @Min(message = FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO, value = 0)
    private int stock;
    @NotNull(message = FieldErrorConstants.BOOK_NAME_CANT_BE_NULL_NOR_EMPTY)
    private String bookName;
}
