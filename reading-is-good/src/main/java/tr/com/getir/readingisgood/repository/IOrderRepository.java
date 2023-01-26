package tr.com.getir.readingisgood.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tr.com.getir.readingisgood.constant.DBQueryConstants;
import tr.com.getir.readingisgood.enums.OrderStatus;
import tr.com.getir.readingisgood.model.Order;

import java.util.Date;
import java.util.List;

public interface IOrderRepository extends CrudRepository<Order, Long> {
    /**
     * This method find orders with pagination and time interval
     *
     * @return list of orders
     */
    @Query(
            value = "SELECT o FROM Order o " +
                    "where " +
                    "o.approvedDate BETWEEN :startDate AND :endDate " +
                    "ORDER BY o.approvedDate LIMIT :limit OFFSET :offset"

    )
    List<Order> findByPaginationAndTimeInterval(Date startDate, Date endDate, int limit, int offset);


    @Query(
            value = "SELECT o FROM Order o " +
                    "inner join o.user u " +
                    "on ( u.id = :userId ) " +
                    "where ( o.approvedDate BETWEEN :startDate AND :endDate )" +
                    "ORDER BY o.approvedDate LIMIT :limit OFFSET :offset"

    )
    List<Order> findByPaginationTimeInternalAndUserId(Long userId, Date startDate, Date endDate, int limit, int offset);

    /**
     * This method find orders by userId
     *
     * @param userId      userId
     * @param orderStatus orderStatus
     * @return list of orders
     */
    @Query(value = "SELECT o FROM Order o inner join o.user u where u.id = :userId and o.orderStatus = :orderStatus")
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    /**
     * This method takes orderId and userId for security reasons. Returns authUser order.
     *
     * @param orderId orderId
     * @param userId  userId
     * @return order
     */
    @Query(value = "SELECT o FROM Order o inner join o.user u where u.id = :userId and o.id =:orderId")
    Order findByUserIdAndOrderId(Long orderId, Long userId);

    /**
     * This method stands for admin. Because this method unsecure. This app must not allow that somebody sees someone else order.
     *
     * @param orderId orderId
     * @return order
     */
    @Query(value = "SELECT o FROM Order o inner join o.user u where o.id =:orderId")
    Order findByOrderId(Long orderId);

    /**
     * Retrieve user statistics
     *
     * @return statistics
     */
    @Query(value =
            "select " +
                    "q1.monthly as " + DBQueryConstants.MONTH + "," +
                    "q1.total_book_count as " + DBQueryConstants.TOTAL_BOOK_COUNT + "," +
                    "q1.total_order_count as " + DBQueryConstants.TOTAL_ORDER_COUNT + "," +
                    "q2.total_item_count as " + DBQueryConstants.TOTAL_PURCHASED_AMOUNT + " "+
                    "from " +
                    "( " +
                    "select " +
                    "DATE_TRUNC('month',o.approved_date) as monthly, " +
                    "SUM(o.item_amount) as total_book_count, " +
                    "COUNT(o.id) as total_order_count " +
                    "from orders o " +
                    "inner join authuser au on au.id=o.user_id  " +
                    "where ( au.id =:userId )  " +
                    "GROUP BY " +
                    "DATE_TRUNC('month',o.approved_date) " +
                    " ) as q1 " +
                    "inner join " +
                    "( " +
                    "select " +
                    "DATE_TRUNC('month',o.approved_date) as monthly, " +
                    "SUM(o.item_amount) as total_item_count " +
                    "from orders o " +
                    "GROUP BY " +
                    "DATE_TRUNC('month',o.approved_date) " +
                    ") as q2 " +
                    "on q1.monthly=q2.monthly",
            nativeQuery = true
    )
    List<Tuple> retrieveStatistics(Long userId);
}
