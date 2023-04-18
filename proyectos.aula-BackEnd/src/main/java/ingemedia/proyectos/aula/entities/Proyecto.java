package ingemedia.proyectos.aula.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ingemedia.proyectos.aula.request.ProyectoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<IntegranteProyecto> integrantes= new ArrayList<>();

    public Proyecto(ProyectoRequest proyectoRequest)
    {
        this.titulo=proyectoRequest.getTitulo();
        this.fecha=proyectoRequest.getFecha();
        this.materia=proyectoRequest.getMateria();
        this.semestre=proyectoRequest.getSemestre();
        this.descripcion=proyectoRequest.getDescripcion();
        this.link=proyectoRequest.getLink();
    }
    
}
