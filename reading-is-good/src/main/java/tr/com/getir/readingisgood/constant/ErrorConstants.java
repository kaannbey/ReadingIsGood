package tr.com.getir.readingisgood.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorConstants {
    /**
     * Login reject error
     */
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "USERNAME_OR_PASSWORD_IS_INCORRECT";
    /**
     * Internal Server Error
     */
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    /**
     * UNAUTHORIZED Error
     */
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    /**
     * Registration exception
     */
    public static final String USERNAME_OR_EMAIL_IS_TAKEN = "USERNAME_OR_EMAIL_IS_TAKEN";
    /**
     * Book insertion error
     */
    public static final String BOOK_HAS_ALREADY_BEEN_INSERTED = "BOOK_HAS_ALREADY_BEEN_INSERTED";
    /**
     * Book update error
     */
    public static final String BOOK_NOT_FOUND = "BOOK_NOT_FOUND";
    /**
     * User not found error
     */
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    /**
     * Order not found error
     */
    public static final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";
    /**
     * Book stock error
     */
    public static final String INSUFFICIENT_BOOK_STOCK = "INSUFFICIENT_BOOK_STOCK";
    /**
     * Order has no items
     */
    public static final String ORDER_ITEMS_EMPTY = "ORDER_ITEMS_EMPTY";
    /**
     * Order null
     */
    public static final String ORDER_CANT_BE_NULL = "ORDER_CANT_BE_NULL";
    /**
     * Pagination DTO entity date field validation constraint
     */
    public static final String START_DATE_AND_END_DATE_CANT_BE_EQUAL = "START_DATE_AND_END_DATE_CANT_BE_EQUAL";
}
