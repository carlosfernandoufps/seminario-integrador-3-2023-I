package ingemedia.proyectos.aula.repositories;

import ingemedia.proyectos.aula.entities.IntegranteProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegranteProyectoRepository extends JpaRepository<IntegranteProyecto, Long> {

  // buscar integrante por codigo
  Optional<IntegranteProyecto> findByIntegrante(String codigo);

  // lista de proyectos por codigo de integrante
  Optional<IntegranteProyecto> findByProyecto(Long proyecto);

}
