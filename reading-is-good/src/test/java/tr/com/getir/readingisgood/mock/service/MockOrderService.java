package tr.com.getir.readingisgood.mock.service;

import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.StatisticDTO;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;

import java.util.List;

public class MockOrderService implements IOrderService {
    @Override
    public OrderDTO buyBook(Long bookId, int amount) {
        return null;
    }

    @Override
    public OrderDTO approveOrder() {
        return null;
    }

    @Override
    public List<OrderDTO> retrieveAllOrders(PaginationDTO paginationDTO) {
        return null;
    }

    @Override
    public List<OrderDTO> retrieveAuthUserOrders(PaginationDTO paginationDTO) {
        return null;
    }

    @Override
    public OrderDTO retrieveAuthUserOrderByOrderId(Long orderId) {
        return null;
    }

    @Override
    public OrderDTO retrieveOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<StatisticDTO> retrieveStatistics(Long userId) {
        return null;
    }
}
