package tr.com.getir.readingisgood.model.dto;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.constant.DBQueryConstants;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
    private String month;
    private Long totalBookCount;
    private Long totalOrderCount;
    private Double totalPurchasedRatio;

    public static List<StatisticDTO> build(List<Tuple> retrieveStatistics) {
        return retrieveStatistics.stream().map(e -> {
                    Calendar c = Calendar.getInstance();
                    c.setTime((Date) e.get(DBQueryConstants.MONTH));
                    String month = Month.of(c.get(Calendar.MONTH) + 1).name();
                    Long totalBookCount = (Long) e.get(DBQueryConstants.TOTAL_BOOK_COUNT);
                    Long totalOrderCount = (Long) e.get(DBQueryConstants.TOTAL_ORDER_COUNT);
                    Double totalPurchasedAmount = totalBookCount.doubleValue() / ((Long) e.get(DBQueryConstants.TOTAL_PURCHASED_AMOUNT)).doubleValue();
                    return new StatisticDTO(
                            month,
                            totalBookCount,
                            totalOrderCount,
                            totalPurchasedAmount
                    );
                }).

                collect(Collectors.toList());
    }
}
