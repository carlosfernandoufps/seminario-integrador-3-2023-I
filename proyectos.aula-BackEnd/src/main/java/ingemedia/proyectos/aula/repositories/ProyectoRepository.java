package ingemedia.proyectos.aula.repositories;

import ingemedia.proyectos.aula.entities.Materia;
import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.responses.MateriaResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

  // optener proyecto por id
  Optional<Proyecto> findById(Long id);

  // consultar un proyecto por semestre
  Optional<List<Proyecto>> findBySemestre(String semestre);

  // consultar un proyecto por materia
  Optional<List<Proyecto>> findByMateria(Materia materia);

  Optional<Proyecto> findByTitulo(String titulo);

  // listar proyectos por codigo de integrante
  // List<Proyecto> findByIntegrante(Integrante integrante);

  // busqueda de proyecto por titulo (ignorando mayusculas y minusculas y acentos)
  Optional<List<Proyecto>> findByTituloContainingIgnoreCase(String titulo);

  // Busqueda por rango de fechas, desde la fecha que se ingresa hasta la fecha
  // actual
  Optional<List<Proyecto>> findByFechaGreaterThanEqual(LocalDate fecha);

  // solicitud en postman para probar la anterior funcion
  // http://localhost:8080/proyectos/fecha?fecha=2020-01-01

  // listar las materias que hay en la base de datos
  @Query(value = "SELECT DISTINCT materia FROM proyecto", nativeQuery = true)
  List<String> findMaterias();

}
