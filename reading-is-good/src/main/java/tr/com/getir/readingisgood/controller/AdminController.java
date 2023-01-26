package tr.com.getir.readingisgood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.getir.readingisgood.constant.Constants;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.constant.RequestParamNameConstants;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.dto.BookDTO;
import tr.com.getir.readingisgood.model.dto.OrderDTO;
import tr.com.getir.readingisgood.model.dto.PaginationDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;
import tr.com.getir.readingisgood.service.interfaces.IAdminService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constants.ADMIN_URL)
public class AdminController {
    @Autowired
    private IAdminService service;

    @Operation(summary = "Register Admin User with AdminRole",
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
                                                    value = ErrorConstants.USERNAME_OR_EMAIL_IS_TAKEN + " , " +
                                                            FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_NAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_SURNAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_USERNAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_EMAIL_CANT_BE_NULL
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
    @PostMapping(
            value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<String>> register(@Valid @RequestBody UserRegistrationDTO registrationDTO,
                                                            @RequestHeader(value = RequestParamNameConstants.ADMIN_HEADER, required = false) String adminHeader) {
        return ResponseEntity.ok(ServiceResponse.success(service.register(registrationDTO, adminHeader)));
    }


    @Operation(summary = "Adds Book",
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
                                                    value = FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO + " , " +
                                                            FieldErrorConstants.BOOK_NAME_CANT_BE_NULL_NOR_EMPTY + " , " +
                                                            ErrorConstants.BOOK_HAS_ALREADY_BEEN_INSERTED
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
    @PostMapping(
            value = "book",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<Book>> addBook(@RequestBody @Valid BookDTO book) {
        return ResponseEntity.ok(ServiceResponse.success(service.addBook(book)));
    }


    @Operation(summary = "Updates Book Stock",
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
                                                    value = FieldErrorConstants.BOOK_STOCK_CANT_BE_LESS_THAN_ZERO + " , " +
                                                            RequestParamNameConstants.BOOK_ID + " , " +
                                                            FieldErrorConstants.BOOK_ID_CANT_BE_NULL
                                            ),
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
                                                    value = ErrorConstants.BOOK_NOT_FOUND

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
    @PutMapping(
            value = "book/stock" + "/{" + RequestParamNameConstants.BOOK_ID + "}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<Book>> updateBook(
            @PathVariable(name = RequestParamNameConstants.BOOK_ID) long bookId,
            @Valid @NotNull(message = FieldErrorConstants.BOOK_ID_CANT_BE_NULL) @RequestBody Integer stock
    ) {
        return ResponseEntity.ok(ServiceResponse.success(service.updateBook(bookId, stock)));
    }


    @Operation(summary = "Searches order by id",
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
                                                    value = "ORDER_ID_CANT_BE_NULL "
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
    @GetMapping(value = "order/{" + RequestParamNameConstants.ORDER_ID + "}")
    public ResponseEntity<ServiceResponse<OrderDTO>> retrieveOrderById(@PathVariable(name = RequestParamNameConstants.ORDER_ID) long bookId) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveOrderById(bookId)));
    }

    @Operation(summary = "Views all orders",
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
            value = "/order/all"
    )
    public ResponseEntity<ServiceResponse<List<OrderDTO>>> retrieveAllOrders(
            @RequestParam(value = RequestParamNameConstants.LIMIT) int limit,
            @RequestParam(value = RequestParamNameConstants.OFFSET) int offset,
            @RequestParam(value = RequestParamNameConstants.START_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = RequestParamNameConstants.END_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveAllOrders(new PaginationDTO(startDate, endDate, limit, offset))));
    }
}
