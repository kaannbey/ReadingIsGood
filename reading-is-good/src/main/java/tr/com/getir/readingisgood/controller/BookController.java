package tr.com.getir.readingisgood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.RequestParamNameConstants;
import tr.com.getir.readingisgood.model.Book;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;
import tr.com.getir.readingisgood.service.interfaces.IBookService;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private IBookService service;


    @Operation(summary = "Retrieve All Books On Market",
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
                                                    value = "LIMIT_CANT_BE_NULL" +
                                                            "OFFSET_CANT_BE_NULL"
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
    @GetMapping(value = "all")
    public ResponseEntity<ServiceResponse<List<Book>>> retrieveBooks(
            @RequestParam(value = RequestParamNameConstants.LIMIT) int limit,
            @RequestParam(value = RequestParamNameConstants.OFFSET) int offset
    ) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveBooks(limit, offset)));
    }

    @Operation(summary = "Retrieve Book by Id",
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
                                                    value = RequestParamNameConstants.BOOK_ID + "CANT_BE_NULL , "
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
    @GetMapping(
            value = "{" + RequestParamNameConstants.BOOK_ID + "}"
    )
    public ResponseEntity<ServiceResponse<Book>> retrieveBooks(
            @PathVariable(name = RequestParamNameConstants.BOOK_ID) long bookId
    ) {
        return ResponseEntity.ok(ServiceResponse.success(service.retrieveBook(bookId)));
    }
}
