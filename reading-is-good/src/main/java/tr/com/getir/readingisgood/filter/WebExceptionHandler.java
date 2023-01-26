package tr.com.getir.readingisgood.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;
import tr.com.getir.readingisgood.model.infra.ServiceResponse;

import java.util.Arrays;
import java.util.Objects;


@ControllerAdvice
public class WebExceptionHandler {
    private static final Logger logger = LogManager.getLogger(WebExceptionHandler.class);

    /**
     * handles custom exceptions
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ServiceResponse<Object>> handleCustomException(CustomException ex, WebRequest request) {
        logger.error("Custom Exception occurred reason:" + ex.getMessage() + " status: " + ex.getStatus());
        return new ResponseEntity<>(ServiceResponse.fail(ex.getMessage(), ex.getStatus()), HttpStatusCode.valueOf(ex.getStatus()));
    }

    /**
     * handles other exceptions
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ServiceResponse<Object>> handleOtherExceptions(Exception ex, WebRequest request) {
        logger.error("Exception occurred reason:" + ex.getMessage());
        return new ResponseEntity<>(ServiceResponse.fail(ErrorConstants.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles constraint validation
     *
     * @param ex exception
     * @return http response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceResponse<Object>> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        logger.error("Constraint Violation Exception occurred reason:" + ex.getMessage());
        return new ResponseEntity<>(ServiceResponse.fail(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Servlet parameter exception
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ServiceResponse<Object>> handleOtherExceptions(MissingServletRequestParameterException ex, WebRequest request) {
        logger.error("MissingServletRequestParameterException Exception occurred reason:" + Arrays.toString(ex.getDetailMessageArguments()));
        return new ResponseEntity<>(ServiceResponse.fail(ex.getParameterName().toUpperCase() + "_CANT_BE_NULL", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Servlet parameter exception
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ServiceResponse<Object>> handleOtherExceptions(AccessDeniedException ex, WebRequest request) {
        logger.error("MissingServletRequestParameterException Exception occurred reason:" + ex.getMessage());
        return new ResponseEntity<>(ServiceResponse.fail(HttpStatus.UNAUTHORIZED.name() + "_CANT_BE_NULL", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }
}
