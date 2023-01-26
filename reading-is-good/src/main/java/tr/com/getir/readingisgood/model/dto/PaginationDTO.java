package tr.com.getir.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO {
    @NotBlank(message = FieldErrorConstants.DATE_CANT_BE_NULL)
    private Date startDate;
    @NotBlank(message = FieldErrorConstants.DATE_CANT_BE_NULL)
    private Date endDate;
    private int limit;
    private int offset;
}
