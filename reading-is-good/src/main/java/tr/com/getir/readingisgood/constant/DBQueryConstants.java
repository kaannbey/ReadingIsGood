package tr.com.getir.readingisgood.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DBQueryConstants {
    /**
     * Month field on statistic query
     */
    public static final String MONTH = "month";
    /**
     * Total book count field on statistic query
     */
    public static final String TOTAL_BOOK_COUNT = "total_book_count";
    /**
     * Total order count field on statistic query
     */
    public static final String TOTAL_ORDER_COUNT = "total_order_count";
    /**
     * Total item count field on statistic query
     */
    public static final String TOTAL_PURCHASED_AMOUNT = "total_purchased_amount";

}
