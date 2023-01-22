package tr.com.getir.readingisgood.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tr.com.getir.readingisgood.constant.ErrorConstants;
import tr.com.getir.readingisgood.exception.CustomException;

@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * handles custom exceptions
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getStatus());
    }

    /**
     * handles other exceptions
     *
     * @param ex      exception
     * @param request webRequest
     * @return http response
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ErrorConstants.INTERNAL_SERVER_ERROR, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
