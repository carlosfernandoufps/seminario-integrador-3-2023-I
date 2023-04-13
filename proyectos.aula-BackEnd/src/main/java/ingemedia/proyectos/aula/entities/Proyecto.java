package ingemedia.proyectos.aula.entities;

import java.time.LocalDate;

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
    
}
