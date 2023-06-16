package ingemedia.proyectos.aula.entities;

import java.util.ArrayList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ingemedia.proyectos.aula.request.MateriaRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Materia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @NotBlank
  private String nombre;

  @OneToMany(orphanRemoval = true, mappedBy = "materia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Proyecto> proyectos = new ArrayList<>();

  public Materia(MateriaRequest materiaRequest) {
    this.nombre = materiaRequest.getNombre();
  }

  public Materia(long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  // crear toString
  public String toString() {
    return "Materia [id=" + id + ", nombre=" + nombre + "]";
  }

}
