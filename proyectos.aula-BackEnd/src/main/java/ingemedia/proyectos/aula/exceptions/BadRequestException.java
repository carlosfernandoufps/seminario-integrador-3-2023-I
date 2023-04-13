package ingemedia.proyectos.aula.exceptions;

import ingemedia.proyectos.aula.responses.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

  private ErrorResponse errorResponse;
}
