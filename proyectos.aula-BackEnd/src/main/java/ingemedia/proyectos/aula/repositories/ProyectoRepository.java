package ingemedia.proyectos.aula.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ingemedia.proyectos.aula.entities.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

}
