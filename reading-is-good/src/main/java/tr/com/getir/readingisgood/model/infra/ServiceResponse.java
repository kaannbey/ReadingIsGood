package tr.com.getir.readingisgood.model.infra;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ServiceResponse<T> {
    private T data;
    private String[] errors;
    private int statusCode;

    /**
     * Success Response with custom statusCode
     *
     * @param data       data
     * @param statusCode http statusCode
     * @return serviceResponse
     */
    public static <T> ServiceResponse<T> success(T data, int statusCode) {
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.data = data;
        serviceResponse.statusCode = statusCode;
        return serviceResponse;
    }

    /**
     * Success Response
     *
     * @param data data
     * @return serviceResponse
     */
    public static <T> ServiceResponse<T> success(T data) {
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.data = data;
        serviceResponse.statusCode = 200;
        return serviceResponse;
    }

    /**
     * Fail Response with custom statusCode
     *
     * @param errors     errors
     * @param statusCode statusCode
     * @return serviceResponse
     */
    public static <T> ServiceResponse<T> fail(String[] errors, int statusCode) {
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.errors = errors;
        serviceResponse.statusCode = statusCode;
        return serviceResponse;
    }

    /**
     * Fail Response with custom statusCode
     *
     * @param error      error
     * @param statusCode statusCode
     * @return serviceResponse
     */
    public static <T> ServiceResponse<T> fail(String error, int statusCode) {
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.errors = new String[]{error};
        serviceResponse.statusCode = statusCode;
        return serviceResponse;
    }
}
