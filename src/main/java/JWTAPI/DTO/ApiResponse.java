package JWTAPI.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
  public ApiResponse(T data) {
    Data = data;
    CodigoHTTP = 200;
    ResultadoIndicador = 1;
    ResultadoDescripcion = "OK";
  }

  String Token;
  int CodigoHTTP;
  String ResultadoDescripcion;
  int ResultadoIndicador;
  T Data;
  List<String> Erros;
}
