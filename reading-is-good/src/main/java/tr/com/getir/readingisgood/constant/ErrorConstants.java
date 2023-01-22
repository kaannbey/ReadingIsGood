package tr.com.getir.readingisgood.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorConstants {
    /**
     * Login reject error
     */
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT =  "USERNAME_OR_PASSWORD_IS_INCORRECT";
    /**
     * Internal Server Error
     */
    public static final String INTERNAL_SERVER_ERROR =  "INTERNAL_SERVER_ERROR";
    /**
     * UNAUTHORIZED Error
     */
    public static final String UNAUTHORIZED =  "UNAUTHORIZED";
    /**
     * Registration exception
     */
    public static final String USERNAME_IS_TAKEN = "USERNAME_IS_TAKEN";
}
