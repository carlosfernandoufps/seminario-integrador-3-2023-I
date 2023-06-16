package ingemedia.proyectos.aula.services;

import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.entities.IntegranteProyecto;
import ingemedia.proyectos.aula.entities.Materia;
import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.IntegranteProyectoRepository;
import ingemedia.proyectos.aula.repositories.MateriaRepository;
import ingemedia.proyectos.aula.repositories.UsuarioRepository;
import ingemedia.proyectos.aula.request.ProyectoRequest;
import ingemedia.proyectos.aula.request.Rol;
import ingemedia.proyectos.aula.repositories.ProyectoRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;
import ingemedia.proyectos.aula.responses.IntegranteResponse;
import ingemedia.proyectos.aula.responses.MateriaResponse;
import ingemedia.proyectos.aula.responses.ProyectoResponse;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProyectoService {

  private final ProyectoRepository proyectoRepository;
  private final UsuarioRepository integranteRepository;
  private final IntegranteProyectoRepository integranteProyectoRepository;
  private final MateriaRepository materiaRepository;

  ProyectoService(ProyectoRepository proyectoRepository, UsuarioRepository integranteRepository,
      IntegranteProyectoRepository integranteProyectoRepository, MateriaRepository materiaRepository) {
    this.proyectoRepository = proyectoRepository;
    this.integranteRepository = integranteRepository;
    this.integranteProyectoRepository = integranteProyectoRepository;
    this.materiaRepository = materiaRepository;
  }

  // optener todos los proyectos
  public List<ProyectoResponse> getProyectos() {
    List<Proyecto> proyectos = proyectoRepository.findAll();
    List<ProyectoResponse> proyectosResponse = new ArrayList<>();
    for (Proyecto proyecto : proyectos) {
      Proyecto proyecto1 = new Proyecto();
      proyecto1.setId(proyecto.getId());
      proyecto1.setTitulo(proyecto.getTitulo());
      proyecto1.setFecha(proyecto.getFecha());
      proyecto1.setSemestre(proyecto.getSemestre());
      proyecto1.setDescripcion(proyecto.getDescripcion());
      proyecto1.setMateria(proyecto.getMateria());
      proyecto1.setLink(proyecto.getLink());
      proyecto1.setImagen(proyecto.getImagen());
      proyecto1.setIntegrantes(proyecto.getIntegrantes());
      // System.out.println("Integrantes: " + proyecto1.getIntegrantes());
      List<IntegranteResponse> integrantes = getProyectoIntegrantes(proyecto1.getId());
      // System.out.println("INTES: " + integrantes.size());
      Long id = proyecto.getMateria().getId();
      String nombre = proyecto.getMateria().getNombre();
      ProyectoResponse proyectoResp = new ProyectoResponse(proyecto1, new Materia(id, nombre), integrantes);
      proyectosResponse.add(proyectoResp);
    }
    return proyectosResponse;

  }

  // optener todas las materias
  public List<MateriaResponse> getMaterias() {
    List<Materia> materias = materiaRepository.findAll();
    List<MateriaResponse> materiasResponse = new ArrayList<>();
    for (Materia materia : materias) {
      materiasResponse.add(new MateriaResponse(materia.getNombre()));
    }
    return materiasResponse;
  }

  // optener un proyecto por id
  public ProyectoResponse getProyecto(Long id) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(id);
    if (proyecto.isPresent()) {
      ProyectoResponse proyectoResponse = new ProyectoResponse();
      proyectoResponse.setProyecto(proyecto.get());
      proyectoResponse
          .setMateria(new Materia(proyecto.get().getMateria().getId(), proyecto.get().getMateria().getNombre()));
      List<IntegranteResponse> integrantes = getProyectoIntegrantes(proyecto.get().getId());
      proyectoResponse.setIntegrantes(integrantes);
      return proyectoResponse;
    } else {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " + id + " no existe"));
    }

  }

  // Registrar un proyecto
  public ProyectoResponse registrarProyecto(Proyecto proyecto, ProyectoRequest proyectoRequest) {

    Optional<Materia> materia = materiaRepository.findByNombre(proyectoRequest.getMateria());

    if (!materia.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("La materia con nombre " + proyectoRequest.getMateria() + " no existe"));
    }

    Optional<Proyecto> proyectoExistente = proyectoRepository.findByTitulo(proyecto.getTitulo());
    if (proyectoExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El proyecto con titulo " + proyecto.getTitulo() + " ya existe"));
    } else {
      proyecto.setMateria(materia.get());
      proyectoRepository.save(proyecto);

      ProyectoResponse proyectoResponse = new ProyectoResponse(proyecto, proyecto.getMateria(), null);
      return proyectoResponse;
    }
  }

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

  public List<Map<String, Object>> getProyectosByCodigoIntegrante(String codigoIntegrante) {

    Optional<Usuario> integrante = integranteRepository.findByCodigo(codigoIntegrante);

    if (!integrante.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
          codigoIntegrante + " no existe"));
    }

    List<IntegranteProyecto> proyectoIntegrante = integrante.get().getProyectos();
    if (proyectoIntegrante.isEmpty()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
          codigoIntegrante + " no tiene proyectos"));
    }

    // listar los proyectos que tiene un integrante
    System.out.println("Aca llega bien");
    List<Object[]> proyectos = integranteRepository.findProyectosByCodigoIntegrante(codigoIntegrante);

    List<Map<String, Object>> result = new ArrayList<>();
    for (Object[] row : proyectos) {
      LocalDate fecha = new LocalDateJavaType().fromString(row[3].toString());
      Map<String, Object> map = new HashMap<>();
      map.put("id", row[0]);
      map.put("titulo", row[1]);
      map.put("descripcion", row[2]);
      map.put("fecha", fecha);
      map.put("imagen", row[4]);
      map.put("link", row[5]);
      map.put("materia", row[6]);
      map.put("semestre", row[7]);
      result.add(map);
    }

    return result;

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
    Optional<Usuario> integrante = integranteRepository.findByCodigo(codigoIntegrante);

    // System.out.println("integrante: " + integrante.get().getNombre());
    // System.out.println("proyecto: " + proyecto.get().getTitulo());

    if (!proyecto.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " +
          idProyecto + " no existe"));
    }

    if (!integrante.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
          codigoIntegrante + " no existe"));
    }

    Proyecto proyectoExistente = proyecto.get();
    Usuario integranteExistente = integrante.get();

    if (integranteExistente.getRol() == Rol.ADMIN) {
      throw new BadRequestException(new ErrorResponse("No se puede agregar un administrador a un proyecto"));
    }

    // verificar que el integrante no este ya en el proyecto
    List<IntegranteProyecto> integrante_p = integranteProyectoRepository.findAll();
    if (!integrante_p.isEmpty()) {
      for (IntegranteProyecto ip : integrante_p) {
        if (ip.getIntegrante().getCodigo().equals(codigoIntegrante) && ip.getProyecto().getId().equals(idProyecto)) {
          throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
              codigoIntegrante + " ya esta en el proyecto con id " + idProyecto));
        }
      }
    }

    IntegranteProyecto integranteProyecto = new IntegranteProyecto();

    integranteProyecto.setProyecto(proyectoExistente);
    integranteProyecto.setIntegrante(integranteExistente);

    // proyectoRepository.save(proyectoExistente);
    // integranteRepository.save(integranteExistente);
    integranteProyectoRepository.save(integranteProyecto);
  }

  // Listar los integrantes de un proyecto
  private List<IntegranteResponse> getProyectoIntegrantes(Long idProyecto) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(idProyecto);

    if (!proyecto.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " +
          idProyecto + " no existe"));
    }

    List<IntegranteProyecto> integrantes = proyecto.get().getIntegrantes();

    List<IntegranteResponse> integrantesResponse = new ArrayList<>();
    for (IntegranteProyecto integrante : integrantes) {
      IntegranteResponse integranteResponse = new IntegranteResponse();
      integranteResponse.setCodigo(integrante.getIntegrante().getCodigo());
      integranteResponse.setNombre(integrante.getIntegrante().getNombre());
      integranteResponse.setApellido(integrante.getIntegrante().getApellido());
      integranteResponse.setCorreo(integrante.getIntegrante().getCorreo());
      integrantesResponse.add(integranteResponse);
    }

    return integrantesResponse;
  }

  // listar los proyectos de un integrante
  public List<ProyectoResponse> getIntegranteProyectos(String codigo) {
    Optional<Usuario> integrante = integranteRepository.findByCodigo(codigo);

    if (!integrante.isPresent()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
          codigo + " no existe"));
    }

    List<IntegranteProyecto> proyectoIntegrante = integrante.get().getProyectos();
    if (proyectoIntegrante.isEmpty()) {
      throw new BadRequestException(new ErrorResponse("El integrante con codigo " +
          codigo + " no tiene proyectos"));
    }

    List<ProyectoResponse> proyectosResponse1 = new ArrayList<>();
    for (IntegranteProyecto ip : proyectoIntegrante) {
      ProyectoResponse proyectoResponse = new ProyectoResponse();
      Proyecto pro = new Proyecto();
      pro.setId(ip.getProyecto().getId());
      pro.setTitulo(ip.getProyecto().getTitulo());
      pro.setFecha(ip.getProyecto().getFecha());
      pro.setSemestre(ip.getProyecto().getSemestre());
      pro.setDescripcion(ip.getProyecto().getDescripcion());
      pro.setLink(ip.getProyecto().getLink());
      pro.setImagen(ip.getProyecto().getImagen());
      proyectoResponse.setProyecto(pro);
      Long idMateria = ip.getProyecto().getMateria().getId();
      String nombreMateria = ip.getProyecto().getMateria().getNombre();
      proyectoResponse.setMateria(new Materia(idMateria, nombreMateria));
      List<IntegranteResponse> integrantes = getProyectoIntegrantes(ip.getProyecto().getId());
      proyectoResponse.setIntegrantes(integrantes);
      // proyectoResponse.setIntegrantes(null);

      proyectosResponse1.add(proyectoResponse);
    }
    return proyectosResponse1;
  }

}
