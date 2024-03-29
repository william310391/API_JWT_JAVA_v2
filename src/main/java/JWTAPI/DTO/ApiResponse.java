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
    ResultadoIndicador = true;
    ResultadoDescripcion = "OK";
  }

  int CodigoHTTP;
  String ResultadoDescripcion;
  Boolean ResultadoIndicador;
  T Data;
  List<String> Erros;
}
