package ingemedia.proyectos.aula.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ingemedia.proyectos.aula.entities.IntegranteProyecto;

@Repository
public interface IntegranteProyectoRepository extends JpaRepository<IntegranteProyecto, Long> {

  // buscar integrante por codigo
  Optional<IntegranteProyecto> findByIntegrante(String codigo);

  // lista de proyectos por codigo de integrante
  Optional<IntegranteProyecto> findByProyecto(Long proyecto);

}
