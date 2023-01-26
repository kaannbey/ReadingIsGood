package tr.com.getir.readingisgood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.model.dto.StatisticDTO;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;
import tr.com.getir.readingisgood.service.interfaces.IStatisticsService;

import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsController {
    @Autowired
    private IStatisticsService service;

    @Operation(summary = "Retrieves Statistics",
            responses = {
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.INTERNAL_SERVER_ERROR
                                            )
                                    },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ServiceResponse<List<StatisticDTO>>> retrieveStatistics() {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveStatistics()));
    }
}
