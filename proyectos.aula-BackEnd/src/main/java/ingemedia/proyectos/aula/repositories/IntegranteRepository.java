package ingemedia.proyectos.aula.repositories;

import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.entities.Proyecto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IntegranteRepository extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByCorreo(String correo);

  // eliminar un integrante por codigo
  void deleteByCodigo(String codigo);

  // consultar un integrante por codigo
  Optional<Usuario> findByCodigo(String codigo);

  // listado de proyectos que tiene un integrante
  @Query(value = "SELECT p.id, p.titulo, p.descripcion, p.fecha, p.imagen, p.link, p.materia, p.semestre FROM integrante i JOIN integrante_proyecto ip ON i.codigo = ip.codigo_integrante JOIN proyecto p ON ip.id_proyecto = p.id WHERE i.codigo = :codigo", nativeQuery = true)
  List<Object[]> findProyectosByCodigoIntegrante(@Param("codigo") String codigo);

}
