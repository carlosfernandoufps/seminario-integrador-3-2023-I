package ingemedia.proyectos.aula.repositories;

import ingemedia.proyectos.aula.entities.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {

  Optional<Integrante> findByCorreo(String correo);

  // eliminar un integrante por codigo
  void deleteByCodigo(String codigo);

  // consultar un integrante por codigo
  Optional<Integrante> findByCodigo(String codigo);

}
