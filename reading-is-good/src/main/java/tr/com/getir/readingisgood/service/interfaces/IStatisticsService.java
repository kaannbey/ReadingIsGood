package tr.com.getir.readingisgood.service.interfaces;

import tr.com.getir.readingisgood.model.dto.StatisticDTO;

import java.util.List;

public interface IStatisticsService {

    List<StatisticDTO> retrieveStatistics();
}
