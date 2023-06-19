package ingemedia.proyectos.aula.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoRequest {

  @NotNull
  @NotBlank
  private String titulo;

  @NotNull
  private LocalDate fecha;

  @NotNull
  private Long idMateria;

  @NotNull
  @NotBlank
  private String semestre;

  @NotNull
  @NotBlank
  private String descripcion;

  @NotNull
  @NotBlank
  private String link;

  @NotNull
  @NotBlank
  private String imagen;

  private List<String> codigosIntegrantes;

}
