package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "integrante_proyecto")
public class IntegranteProyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    // @MapsId("codigoIntegrante")
    @JoinColumn(name = "codigo_integrante")
    private Integrante integrante;

    @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("idProyecto")
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

}
