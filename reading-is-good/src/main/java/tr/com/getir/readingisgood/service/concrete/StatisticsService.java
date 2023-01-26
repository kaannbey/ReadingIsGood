package tr.com.getir.readingisgood.service.concrete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.getir.readingisgood.filter.AuthTokenFilter;
import tr.com.getir.readingisgood.model.dto.StatisticDTO;
import tr.com.getir.readingisgood.model.infra.AuthUserDetails;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;
import tr.com.getir.readingisgood.service.interfaces.IStatisticsService;

import java.util.List;

@Service
public class StatisticsService implements IStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private IOrderService orderService;

    public List<StatisticDTO> retrieveStatistics(){
        AuthUserDetails userDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.retrieveStatistics(userDetails.getId());
    }
}
