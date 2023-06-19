package ingemedia.proyectos.aula.responses;

import ingemedia.proyectos.aula.request.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseJwt {

  private String codigo;
  private String nombre;
  private String apellido;
  private String correo;
  private Rol rol;

}
