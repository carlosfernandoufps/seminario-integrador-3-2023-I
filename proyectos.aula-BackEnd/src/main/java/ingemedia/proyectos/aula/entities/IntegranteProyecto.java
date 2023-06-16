package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @Table(name = "proyecto_integrantes")
public class IntegranteProyecto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "codigo_integrante")
  private Usuario integrante;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "id_proyecto")
  private Proyecto proyecto;

  public String toString() {
    return "IntegranteProyecto(id=" + this.getId() + ", integrante=" + this.getIntegrante() + ", proyecto="
        + this.getProyecto() + ")";
  }

}
