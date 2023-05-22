package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ingemedia.proyectos.aula.request.ProyectoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String titulo;

    @NotNull
    private LocalDate fecha;

    @NotNull
    @NotBlank
    private String materia;

    @NotNull
    @NotBlank
    private String semestre;

    @NotNull
    @NotBlank
    private String descripcion;

    @NotNull
    @NotBlank
    private String link;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinTable(name = "integrante_proyecto", joinColumns = @JoinColumn(name =
    // "id_proyecto"), inverseJoinColumns = @JoinColumn(name = "codigo_integrante"))
    private List<Integrante> integrantes = new ArrayList<>();

    public Proyecto(ProyectoRequest proyectoRequest) {
        this.titulo = proyectoRequest.getTitulo();
        this.fecha = proyectoRequest.getFecha();
        this.materia = proyectoRequest.getMateria();
        this.semestre = proyectoRequest.getSemestre();
        this.descripcion = proyectoRequest.getDescripcion();
        this.link = proyectoRequest.getLink();
    }

    // agregar integrante
    public void addIntegrante(Integrante integrante) {
        this.integrantes.add(integrante);
    }

}
