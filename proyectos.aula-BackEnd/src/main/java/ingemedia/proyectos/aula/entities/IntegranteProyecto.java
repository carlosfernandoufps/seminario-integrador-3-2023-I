package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Integrante_Proyecto")
public class IntegranteProyecto {
    @EmbeddedId
    private IntegranteProyectoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @MapsId("codigo_integrante")
    private Integrante integrante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_proyecto")
    private Proyecto proyecto;


    
    
}
