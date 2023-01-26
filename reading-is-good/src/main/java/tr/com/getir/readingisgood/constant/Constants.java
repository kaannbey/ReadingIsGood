package tr.com.getir.readingisgood.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    /**
     * Specifies http response
     */
    public static final String OK = "OK";
    /**
     * Specifies admin specific operation
     */
    public static final String ADMIN_URL = "/admin";
    /**
     * Specifies admin secret key
     */
    public static final String ADMIN_SECRET = "admin.secret";
    /**
     * Specifies admin secret key
     */
    public static final String DEV_MODE = "application.mode.dev";
}
