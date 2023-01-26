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
     * Buy Book dto book id field validation constraint
     */
    public static final String BOOK_ID_CANT_BE_NULL = "BOOK_ID_CANT_BE_NULL";
    /**
     * Book entity book stock field validation constraint
     */
    public static final String BOOK_STOCK_CANT_BE_LESS_THAN_ZERO = "BOOK_STOCK_CANT_BE_LESS_THAN_ZERO";
    /**
     * BookDTO book amount field validation constraint
     */
    public static final String BOOK_AMOUNT_CANT_BE_LESS_THAN_ONE = "BOOK_AMOUNT_CANT_BE_LESS_THAN_ONE";
    /**
     * User entity user name field validation constraint
     */
    public static final String USER_NAME_CANT_BE_NULL = "USER_NAME_CANT_BE_NULL";
    /**
     * User entity user surname field validation constraint
     */
    public static final String USER_SURNAME_CANT_BE_NULL = "USER_SURNAME_CANT_BE_NULL";
    /**
     * User entity user username field validation constraint
     */
    public static final String USER_USERNAME_CANT_BE_NULL = "USER_USERNAME_CANT_BE_NULL";
    /**
     * User entity user password field validation constraint
     */
    public static final String USER_PASSWORD_CANT_BE_NULL = "USER_PASSWORD_CANT_BE_NULL";
    /**
     * User entity user email field validation constraint
     */
    public static final String USER_EMAIL_CANT_BE_NULL = "USER_EMAIL_CANT_BE_NULL";
    /**
     * Order entity item amount field validation constraint
     */
    public static final String ORDER_ITEM_AMOUNT_MUST_BE_GREATER_THAN_ONE = "ORDER_ITEM_AMOUNT_MUST_BE_GREATER_THAN_ONE";
    /**
     * Pagination DTO entity date field validation constraint
     */
    public static final String DATE_CANT_BE_NULL = "DATE_CANT_BE_NULL";


}
