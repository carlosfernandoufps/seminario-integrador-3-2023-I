package ingemedia.proyectos.aula.controllers;

import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.request.ProyectoRequest;
import ingemedia.proyectos.aula.responses.MateriaResponse;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import ingemedia.proyectos.aula.responses.ProyectoResponse;
import ingemedia.proyectos.aula.services.ProyectoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/proyectos")
@Tag(name = "Proyectos", description = "API para la gestion de proyectos")
public class ProyectoController {

  private final ProyectoService proyectoService;

  ProyectoController(ProyectoService proyectoService) {
    this.proyectoService = proyectoService;
  }

  // obtener todos los proyectos
  @GetMapping
  public List<ProyectoResponse> getProyectos() {
    try {
      List<ProyectoResponse> proyectos = proyectoService.getProyectos();
      return proyectos;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }

  }

  // obtener un proyecto por id
  @GetMapping("/{id}")
  public ProyectoResponse getProyecto(@PathVariable Long id) {
    return proyectoService.getProyecto(id);
  }

  // Registrar un proyecto
  @PostMapping
  public ProyectoResponse registrarProyecto(@RequestBody @Valid ProyectoRequest proyecto) {
    ProyectoResponse proyectoNuevo = proyectoService.registrarProyecto(new Proyecto(proyecto), proyecto,
        proyecto.getCodigosIntegrantes());
    return proyectoNuevo;
  }

  // actualizar un proyecto
  @PutMapping("/{id}")
  public Proyecto actualizarProyecto(@PathVariable Long id, @RequestBody @Valid ProyectoRequest proyecto) {
    return proyectoService.actualizarProyecto(id, new Proyecto(proyecto));
  }

  // Eliminar un proyecto
  @DeleteMapping("/{id}")
  public MensajeResponse eliminarProyecto(@PathVariable Long id) {
    proyectoService.eliminarProyecto(id);
    return new MensajeResponse("El integrante con el codigo " + id + " fue eliminado");
  }

  // Obtener Proyectos por semestre
  @GetMapping("/semestre/{semestre}")
  public List<ProyectoResponse> getProyectosBySemestre(@PathVariable String semestre) {
    return proyectoService.getProyectosBySemestre(semestre);
  }

  // Optener Proyectos por materia
  @GetMapping("/materia/{idMateria}")
  public List<ProyectoResponse> getProyectosByMateria(@PathVariable Long idMateria) {
    return proyectoService.getProyectosByMateria(idMateria);
  }

  @GetMapping("/materias")
  public List<ProyectoResponse> getProyectosByMaterias(@RequestParam List<Long> materias) {
    Set<ProyectoResponse> proyectoSet = new HashSet<>();
    for (Long materia : materias) {
      proyectoSet.addAll(proyectoService.getProyectosByMateria(materia));
    }
    return proyectoSet.stream().toList();
  }

  // listar los proyectos que tiene un integrante
  @GetMapping("/integrante/{codigoIntegrante}")
  public List<ProyectoResponse> getProyectosByCodigoIntegrante(@PathVariable String codigoIntegrante) {

    return proyectoService.getIntegranteProyectos(codigoIntegrante);

  }

  // busqueda de proyecto por titulo (ignorando mayusculas y minusculas y acentos)
  @GetMapping("/titulo/{titulo}")
  public List<ProyectoResponse> getProyectosByTitulo(@PathVariable String titulo) {
    return proyectoService.getProyectosByTitulo(titulo);
  }

  // Busqueda por rango de fechas, desde la fecha que se ingresa hasta la fecha
  // actual
  // solicitud en postman para probar la anterior funcion
  // http://localhost:8080/proyectos/fecha?fecha=2020-01-01
  @GetMapping("/fecha/{fecha}")
  public List<ProyectoResponse> getProyectosByFecha(@PathVariable LocalDate fecha) {
    return proyectoService.getProyectosByFecha(fecha);
  }

  // agregar un integrante a un proyecto
  @PutMapping("/integrante/{codigoIntegrante}/proyecto/{idProyecto}")
  public MensajeResponse agregarIntegranteAProyecto(@PathVariable String codigoIntegrante,
      @PathVariable Long idProyecto) {
    proyectoService.agregarIntegranteAProyecto(idProyecto, codigoIntegrante);
    return new MensajeResponse(
        "El integrante con el codigo " + codigoIntegrante + " fue agregado al proyecto con el id "
            + idProyecto);
  }

  // listar las materias que hay en la base de datos
  @GetMapping("/materias-registradas")
  public List<MateriaResponse> getMaterias() {
    return proyectoService.getMaterias();
  }

}
