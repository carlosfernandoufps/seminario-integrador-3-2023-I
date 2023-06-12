package ingemedia.proyectos.aula.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ingemedia.proyectos.aula.entities.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

  Optional<Materia> findByNombre(String nombre);

}
