package ingemedia.proyectos.aula.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ingemedia.proyectos.aula.entities.Integrante;
import ingemedia.proyectos.aula.entities.IntegranteProyecto;
import ingemedia.proyectos.aula.entities.IntegranteProyectoId;
import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.IntegranteProyectoRepository;
import ingemedia.proyectos.aula.repositories.IntegranteRepository;
import ingemedia.proyectos.aula.repositories.ProyectoRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;

@Service
public class ProyectoService {

  private final ProyectoRepository proyectoRepository;
  private final IntegranteRepository integranteRepository;
  private final IntegranteProyectoRepository integranteProyectoRepository;

  ProyectoService(ProyectoRepository proyectoRepository, IntegranteRepository integranteRepository,
      IntegranteProyectoRepository integranteProyectoRepository) {
    this.proyectoRepository = proyectoRepository;
    this.integranteRepository = integranteRepository;
    this.integranteProyectoRepository = integranteProyectoRepository;
  }

  // optener todos los proyectos
  public List<Proyecto> getProyectos() {
    return proyectoRepository.findAll();

  }

  // optener un proyecto por id
  public Proyecto getProyecto(Long id) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(id);
    if (proyecto.isPresent()) {
      return proyecto.get();
    } else {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " + id + " no existe"));
    }

  }

  // Registrar un proyecto
  public Proyecto registrarProyecto(Proyecto proyecto) {
    Optional<Proyecto> proyectoExistente = proyectoRepository.findByTitulo(proyecto.getTitulo());
    if (proyectoExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El proyecto con titulo " + proyecto.getTitulo() + " ya existe"));
    } else {
      return proyectoRepository.save(proyecto);
    }
  }

  // CONTINUAR ACA!!!!!
  // Eliminar un proyecto
  public void eliminarProyecto(Long id) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(id);
    if (proyecto.isPresent()) {
      proyectoRepository.deleteById(id);
    } else {
      throw new BadRequestException(new ErrorResponse("El Proyecto con id " + id + " no existe"));
    }
  }

  // Actualizar un proyecto
  public Proyecto actualizarProyecto(Long id, Proyecto proyecto) {
    Optional<Proyecto> proyectoOptional = proyectoRepository.findById(id);
    if (!proyectoOptional.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El Proyecto con id " + id + " no existe"));
    }
    Proyecto proyectoExistente = proyectoOptional.get();

    // Actualizar el proyecto existente con los nuevos datos
    proyectoExistente.setTitulo(proyecto.getTitulo());
    proyectoExistente.setDescripcion(proyecto.getDescripcion());
    proyectoExistente.setFecha(proyecto.getFecha());
    proyectoExistente.setLink(proyecto.getLink());
    proyectoExistente.setSemestre(proyecto.getSemestre());
    proyectoExistente.setMateria(proyecto.getMateria());

    Proyecto proyectoActualizado = proyectoRepository.save(proyectoExistente);

    return proyectoActualizado;

  }

  // optener proyectos por semestre
  public List<Proyecto> getProyectosBySemestre(String semestre) {
    Optional<List<Proyecto>> proyectos = proyectoRepository.findBySemestre(semestre);
    if (proyectos.isPresent()) {
      return proyectos.get();
    } else {
      throw new BadRequestException(new ErrorResponse("No hay proyectos para el semestre " + semestre));
    }
  }

  // optener proyectos por materia
  public List<Proyecto> getProyectosByMateria(String materia) {
    Optional<List<Proyecto>> proyectos = proyectoRepository.findByMateria(materia);
    if (proyectos.isPresent()) {
      return proyectos.get();
    } else {
      throw new BadRequestException(new ErrorResponse("No hay proyectos para la materia " + materia));
    }
  }

  // listar los proyectos que tiene un integrante

  public List<Proyecto> getProyectosByCodigoIntegrante(String codigoIntegrante) {

    Optional<Integrante> integrante = integranteRepository.findByCodigo(codigoIntegrante);

    if (!integrante.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " + codigoIntegrante + " no existe"));
    }

    List<Proyecto> proyectos = integrante.get().getProyectos();

    return proyectos;

  }

  // busqueda de proyecto por titulo (ignorando mayusculas y minusculas y acentos)
  public List<Proyecto> getProyectosByTitulo(String titulo) {
    Optional<List<Proyecto>> proyectos = proyectoRepository.findByTituloContainingIgnoreCase(titulo);

    if (proyectos.get().isEmpty()) {
      throw new BadRequestException(new ErrorResponse("No hay proyectos con el titulo " + titulo));
    }

    return proyectos.get();
  }

  // Busqueda por rango de fechas, desde la fecha que se ingresa hasta la fecha
  // actual
  public List<Proyecto> getProyectosByFecha(LocalDate fecha) {
    Optional<List<Proyecto>> proyectos = proyectoRepository.findByFechaGreaterThanEqual(fecha);

    if (proyectos.get().isEmpty()) {
      throw new BadRequestException(new ErrorResponse("No hay proyectos con fecha mayor o igual a " + fecha));
    }

    return proyectos.get();
  }

  // agregar un integrante a un proyecto
  public void agregarIntegranteAProyecto(Long idProyecto, String codigoIntegrante) {

    // System.out.println("proyecto que llega: " + idProyecto);

    Optional<Proyecto> proyecto = proyectoRepository.findById(idProyecto);
    Optional<Integrante> integrante = integranteRepository.findByCodigo(codigoIntegrante);

    // System.out.println("integrante: " + integrante.get().getNombre());
    // System.out.println("proyecto: " + proyecto.get().getTitulo());

    if (!proyecto.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " + idProyecto + " no existe"));
    }

    if (!integrante.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " + codigoIntegrante + " no existe"));
    }

    Proyecto proyectoExistente = proyecto.get();
    Integrante integranteExistente = integrante.get();

    proyectoExistente.addIntegrante(integranteExistente);
    integranteExistente.addProyecto(proyectoExistente);

    proyectoRepository.save(proyectoExistente);
    integranteRepository.save(integranteExistente);

    IntegranteProyecto integranteProyecto = new IntegranteProyecto();
    // integranteProyecto.setId(new
    // IntegranteProyectoId(integranteExistente.getCodigo(),
    // proyectoExistente.getId()));
    integranteProyecto.setProyecto(proyectoExistente);
    integranteProyecto.setIntegrante(integranteExistente);

    integranteProyectoRepository.save(integranteProyecto);
  }

}
