package tr.com.getir.readingisgood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class CustomException extends RuntimeException {
    private final int status;

    public CustomException(String err,int status) {
        super(err);
        this.status = status;
    }

    public CustomException(String[] err,int status) {
        super(Arrays.toString(err));
        this.status = status;
    }


}
