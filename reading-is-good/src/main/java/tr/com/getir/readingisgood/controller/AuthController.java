package tr.com.getir.readingisgood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.constant.FieldErrorConstants;
import tr.com.getir.readingisgood.model.dto.UserLoginDTO;
import tr.com.getir.readingisgood.model.dto.UserRegistrationDTO;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;
import tr.com.getir.readingisgood.service.interfaces.IAuthService;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private IAuthService service;


    @Operation(summary = "Registers new user with UserRole",
            responses = {
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.INTERNAL_SERVER_ERROR
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = FieldErrorConstants.USER_NAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_SURNAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_USERNAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_EMAIL_CANT_BE_NULL + " , " +
                                                            ErrorConstants.USERNAME_OR_EMAIL_IS_TAKEN
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
    public ResponseEntity<ServiceResponse<String>> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        return ResponseEntity.ok(ServiceResponse.success(service.register(registrationDTO, false)));
    }

    @Operation(summary = "Login Operation",
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
                                                    value = FieldErrorConstants.USER_USERNAME_CANT_BE_NULL + " , " +
                                                            FieldErrorConstants.USER_PASSWORD_CANT_BE_NULL
                                            )
                                    },
                                    schema = @Schema(implementation = ServiceResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content =
                            @Content(
                                    examples = {
                                            @ExampleObject(
                                                    value = ErrorConstants.USERNAME_OR_PASSWORD_IS_INCORRECT
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
            value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse<String>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        return ResponseEntity.ok(ServiceResponse.success(service.login(loginDTO)));
    }

}
