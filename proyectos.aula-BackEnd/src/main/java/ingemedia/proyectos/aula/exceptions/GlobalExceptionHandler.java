package ingemedia.proyectos.aula.exceptions;

import ingemedia.proyectos.aula.responses.ErrorResponse;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorResponse());
  }

  // para validar que los campos esten completos y en el formato correcto
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public MensajeResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    return new MensajeResponse(mensaje);
  }

  // @ExceptionHandler(MethodArgumentNotValidException.class)
  // public ResponseEntity<String>
  // handleValidationException(MethodArgumentNotValidException ex) {
  // // Aquí puedes realizar cualquier lógica adicional que necesites

  // // Obtener los mensajes de error de la excepción
  // String errorMessage = ex.getBindingResult().getAllErrors()
  // .stream()
  // .map(error -> error.getDefaultMessage())
  // .collect(Collectors.joining(", "));

  // // Devolver una respuesta con el mensaje de error
  // return ResponseEntity.badRequest().body(errorMessage);
  // }

}
