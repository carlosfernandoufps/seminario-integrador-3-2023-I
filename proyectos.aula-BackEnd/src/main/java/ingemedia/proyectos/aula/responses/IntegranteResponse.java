package ingemedia.proyectos.aula.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntegranteResponse {
  private String codigo;
  private String nombre;
  private String apellido;
  private String correo;
}
