package tr.com.getir.readingisgood.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FieldErrorConstants {
    /**
     * Book entity book name field validation constraint
     */
    public static final String BOOK_NAME_CANT_BE_NULL_NOR_EMPTY = "BOOK_NAME_CANT_BE_NULL_NOR_EMPTY";
    /**
     * Book entity book stock field validation constraint
     */
    public static final String BOOK_STOCK_CANT_BE_LESS_THAN_ZERO = "BOOK_STOCK_CANT_BE_LESS_THAN_ZERO";
    /**
     * Customer entity customer name field validation constraint
     */
    public static final String CUSTOMER_NAME_CANT_BE_NULL = "CUSTOMER_NAME_CANT_BE_NULL";
    /**
     * Customer entity customer surname field validation constraint
     */
    public static final String CUSTOMER_SURNAME_CANT_BE_NULL = "CUSTOMER_SURNAME_CANT_BE_NULL";
    /**
     * Customer entity customer username field validation constraint
     */
    public static final String CUSTOMER_USERNAME_CANT_BE_NULL = "CUSTOMER_USERNAME_CANT_BE_NULL";
    /**
     * Customer entity customer password field validation constraint
     */
    public static final String CUSTOMER_PASSWORD_CANT_BE_NULL = "CUSTOMER_PASSWORD_CANT_BE_NULL";


}
