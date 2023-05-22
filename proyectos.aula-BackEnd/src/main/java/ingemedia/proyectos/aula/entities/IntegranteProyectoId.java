package ingemedia.proyectos.aula.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntegranteProyectoId {

    private String codigoIntegrante;
    private Long idProyecto;

}
