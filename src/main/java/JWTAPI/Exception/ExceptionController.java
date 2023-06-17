package JWTAPI.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import JWTAPI.DTO.ApiResponse;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> BusinessExceptionHandler(BusinessException ex) {
        ApiResponse<String> response = new ApiResponse<String>(null);
        response.setCodigoHTTP(ex.getStatus().value());
        response.setResultadoDescripcion(ex.getMessage());
        response.setResultadoIndicador(0);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ApiResponse<String> response = new ApiResponse<String>(null);
        response.setCodigoHTTP(HttpStatus.BAD_REQUEST.value());
        response.setResultadoDescripcion("Existen campos que son requeridos");
        response.setResultadoIndicador(0);

        List<String> lista = new ArrayList<String>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName =  "pruebaaa";//((FieldError) error).getField();
            String message = error.getDefaultMessage();
            lista.add(fieldName+ " : " + message);
        });
        response.setErros(lista);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

}

