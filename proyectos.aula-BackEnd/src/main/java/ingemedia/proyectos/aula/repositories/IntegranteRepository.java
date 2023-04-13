package ingemedia.proyectos.aula.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ingemedia.proyectos.aula.entities.Integrante;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Integer> {

  Optional<Integrante> findByCorreo(String correo);

}
