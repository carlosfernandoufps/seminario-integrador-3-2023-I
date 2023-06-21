package ingemedia.proyectos.aula.auth;

import ingemedia.proyectos.aula.request.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotNull
  @NotBlank
  private String codigo;
  @NotNull
  @NotBlank
  private String nombre;
  @NotNull
  @NotBlank
  private String apellido;
  @NotNull
  @NotBlank
  @Email
  private String correo;
  @NotNull
  @NotBlank
  private String password;
  @NotNull
  private Rol rol;

}
