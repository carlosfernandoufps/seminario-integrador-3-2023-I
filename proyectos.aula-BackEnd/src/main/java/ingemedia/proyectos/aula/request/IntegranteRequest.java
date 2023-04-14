package ingemedia.proyectos.aula.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IntegranteRequest {

  @NotNull(message = "El campo codigo no puede ser nulo")
  private Integer codigo;

  @NotBlank(message = "El campo nombre no puede estar vacio")
  private String nombre;

  @NotBlank(message = "El campo apellido no puede estar vacio")
  private String apellido;

  @NotBlank(message = "El campo correo no puede estar vacio")
  @Email(message = "El correo electronico no es valido")
  private String correo;

  @NotNull(message = "El campo rol no puede ser nulo")
  private Rol rol;
}
