package ingemedia.proyectos.aula.responses;

import ingemedia.proyectos.aula.entities.Proyecto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResponse {
  private Proyecto proyecto;
  private String materia;
}
