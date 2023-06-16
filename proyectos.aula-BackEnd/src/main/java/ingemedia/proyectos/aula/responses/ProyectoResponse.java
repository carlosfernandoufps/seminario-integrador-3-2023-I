package ingemedia.proyectos.aula.responses;

import java.util.ArrayList;
import java.util.List;

import ingemedia.proyectos.aula.entities.Materia;
import ingemedia.proyectos.aula.entities.Proyecto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProyectoResponse {
  private Proyecto proyecto;
  private Materia materia;
  private List<IntegranteResponse> integrantes = new ArrayList<>();

  public String toString() {
    return "ProyectoResponse(proyecto=" + this.getProyecto() + ", materia=" + this.getMateria() + ")";
  }
}
