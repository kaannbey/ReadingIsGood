
package tr.com.getir.readingisgood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.enums.OrderStatus;
import tr.com.getir.readingisgood.model.infra.AEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends AEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_item_id")
    private Set<ShoppingItem> items = new HashSet<>();

    @Min(message = FieldErrorConstants.ORDER_ITEM_AMOUNT_MUST_BE_GREATER_THAN_ONE, value = 1)
    @Column(name = "itemAmount")
    private int itemAmount;

    @Column(name = "orderStatus")
    private OrderStatus orderStatus;

    @Column(name = "approvedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    public void approveOrder() {
        setApprovedDate(new Date());
        setOrderStatus(OrderStatus.APPROVED);
    }

    public static Order build(AuthUser user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.ON_SHOPPING_CART);
        user.getOrders().add(order);
        return order;
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        item.setOrder(this);
        itemAmount += item.getAmount();
    }
}

