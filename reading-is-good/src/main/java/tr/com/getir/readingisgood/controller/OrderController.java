package tr.com.getir.readingisgood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.RequestParamNameConstants;
import tr.com.getir.readingisgood.model.dto.BuyBookDTO;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;
import tr.com.getir.readingisgood.service.interfaces.IOrderService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IOrderService service;

    @Operation(summary = "Buys book ( Adds to shopping cart )",
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
                            responseCode = "400",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.INSUFFICIENT_BOOK_STOCK
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.BOOK_NOT_FOUND
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<OrderDTO>> buyBook(@Valid @RequestBody BuyBookDTO bookDTO) {
        return ResponseEntity.ok(ServiceResponse.success(service.buyBook(bookDTO.getBookId(), bookDTO.getAmount())));
    }

    @Operation(summary = "Approves order ( Changes order status shopping_cart to approved )",
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
                            responseCode = "400",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.ORDER_CANT_BE_NULL + " , " +
                                                            ErrorConstants.INSUFFICIENT_BOOK_STOCK+ " , " +
                                                            ErrorConstants.ORDER_ITEMS_EMPTY
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @PostMapping(
            value = "approve",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<OrderDTO>> approveOrder() {
        return ResponseEntity.ok(ServiceResponse.success(service.approveOrder()));
    }

    @Operation(summary = "Retrieves Authenticated user order by id ( Order must belongs to authenticated user)",
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
                            responseCode = "404",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.ORDER_NOT_FOUND
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping(value = "{" + RequestParamNameConstants.ORDER_ID + "}")
    public ResponseEntity<ServiceResponse<OrderDTO>> retrieve(@PathVariable(name = RequestParamNameConstants.ORDER_ID) long bookId) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveAuthUserOrderByOrderId(bookId)));
    }


    @Operation(summary = "Retrieves all orders of authenticated user",
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
                            responseCode = "400",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = "LIMIT_CANT_BE_NULL , " +
                                                            "OFFSET_CANT_BE_NULL , " +
                                                            "START_DATE_CANT_BE_NULL , " +
                                                            "END_DATE_CANT_BE_NULL , "
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping(
            value = "/all"
    )
    public ResponseEntity<ServiceResponse<List<OrderDTO>>> retrieve(
            @RequestParam(value = RequestParamNameConstants.LIMIT) int limit,
            @RequestParam(value = RequestParamNameConstants.OFFSET) int offset,
            @RequestParam(value = RequestParamNameConstants.START_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = RequestParamNameConstants.END_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveAuthUserOrders(new PaginationDTO(startDate, endDate, limit, offset))));
    }
}
