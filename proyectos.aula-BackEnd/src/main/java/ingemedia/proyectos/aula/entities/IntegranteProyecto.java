package ingemedia.proyectos.aula.entities;

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

    @ManyToOne
    @MapsId("codigo_integrante")
    private Integrante integrante;

    @ManyToOne
    @MapsId("id_proyecto")
    private Proyecto proyecto;


    
    
}
