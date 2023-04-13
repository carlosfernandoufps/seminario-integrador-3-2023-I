package ingemedia.proyectos.aula.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IntegranteRequest {

  @NotNull
  @NotBlank
  private int codigo;

  @NotNull
  @NotBlank
  private String nombre;
  @NotNull
  @NotBlank
  private String apellido;
  @NotNull
  @NotBlank
  private String correo;
  @NotNull
  @NotBlank
  private Rol rol;
}
