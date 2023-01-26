package tr.com.getir.readingisgood.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.enums.OrderStatus;
import tr.com.getir.readingisgood.model.Order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDTO {
    private Long id;
    private Set<ShoppingItemDTO> items = new HashSet<>();
    private int itemAmount;
    private OrderStatus status;
    private Date approvedDate;

    public static OrderDTO build(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setItemAmount(order.getItemAmount());
        orderDTO.setItems(order.getItems().stream().map(ShoppingItemDTO::new).collect(Collectors.toSet()));
        orderDTO.setStatus(order.getOrderStatus());
        orderDTO.setApprovedDate(order.getApprovedDate());
        return orderDTO;
    }
}
