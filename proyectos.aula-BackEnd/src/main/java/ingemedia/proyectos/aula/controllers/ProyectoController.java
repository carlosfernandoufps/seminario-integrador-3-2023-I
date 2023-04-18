package ingemedia.proyectos.aula.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import ingemedia.proyectos.aula.entities.Integrante;
import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.services.ProyectoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import ingemedia.proyectos.aula.request.*;

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
    public Proyecto getProyecto(@PathVariable int id) {
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
    public Proyecto actualizarProyecto(@PathVariable int id, @RequestBody @Valid IntegranteRequest proyecto) {
        return proyectoService.actualizarProyecto(id, new Integrante(proyecto));
    }

    // Eliminar un integrante
    @DeleteMapping("/{id}")
    public MensajeResponse eliminarIntegrante(@PathVariable int codigo) {
        proyectoService.eliminarIntegrante(codigo);

        return new MensajeResponse("El integrante con el codigo " + codigo + " fue eliminado");
    }
    
}
