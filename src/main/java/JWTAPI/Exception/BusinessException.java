package JWTAPI.Exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private HttpStatus status;
    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

