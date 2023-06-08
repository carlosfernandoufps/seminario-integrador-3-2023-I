package ingemedia.proyectos.aula.controllers;

import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.request.ProyectoRequest;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import ingemedia.proyectos.aula.services.ProyectoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public List<Proyecto> getIntegrantes() {
        return proyectoService.getProyectos();
    }

    // obtener un proyecto por id
    @GetMapping("/{id}")
    public Proyecto getProyecto(@PathVariable Long id) {
        return proyectoService.getProyecto(id);
    }

    // Registrar un proyecto
    @PostMapping
    public Proyecto registrarProyecto(@RequestBody @Valid ProyectoRequest proyecto) {
        Proyecto proyectoNuevo = proyectoService.registrarProyecto(new Proyecto(proyecto));
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
    public List<Proyecto> getProyectosBySemestre(@PathVariable String semestre) {
        return proyectoService.getProyectosBySemestre(semestre);
    }

    // Optener Proyectos por materia
    @GetMapping("/materia/{materia}")
    public List<Proyecto> getProyectosByMateria(@PathVariable String materia) {
        return proyectoService.getProyectosByMateria(materia);
    }

    // listar los proyectos que tiene un integrante
    @GetMapping("/integrante/{codigoIntegrante}")
    public List<Map<String, Object>> getProyectosByCodigoIntegrante(@PathVariable String codigoIntegrante) {

        return proyectoService.getProyectosByCodigoIntegrante(codigoIntegrante);

    }

    // busqueda de proyecto por titulo (ignorando mayusculas y minusculas y acentos)
    @GetMapping("/titulo/{titulo}")
    public List<Proyecto> getProyectosByTitulo(@PathVariable String titulo) {
        return proyectoService.getProyectosByTitulo(titulo);
    }

    // Busqueda por rango de fechas, desde la fecha que se ingresa hasta la fecha
    // actual
    // solicitud en postman para probar la anterior funcion
    // http://localhost:8080/proyectos/fecha?fecha=2020-01-01
    @GetMapping("/fecha/{fecha}")
    public List<Proyecto> getProyectosByFecha(@PathVariable LocalDate fecha) {
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

}
