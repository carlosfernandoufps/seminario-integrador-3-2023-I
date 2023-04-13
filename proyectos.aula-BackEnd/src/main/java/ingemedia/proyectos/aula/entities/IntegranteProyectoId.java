package ingemedia.proyectos.aula.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class IntegranteProyectoId {
    private Integer codigo_integrante;
    private Integer id_proyecto;
}
