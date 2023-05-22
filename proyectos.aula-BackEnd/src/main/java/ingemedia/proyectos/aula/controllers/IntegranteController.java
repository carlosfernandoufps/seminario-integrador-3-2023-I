package ingemedia.proyectos.aula.controllers;

import ingemedia.proyectos.aula.entities.Integrante;
import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import ingemedia.proyectos.aula.services.IntegranteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/integrantes")
@Tag(name = "Integrantes", description = "API para la gestion de integrantes")
public class IntegranteController {

    private final IntegranteService integranteService;

    IntegranteController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    // obtener todos los integrantes
    @GetMapping
    public List<Integrante> getIntegrantes() {
        return integranteService.getIntegrantes();
    }

    // obtener un integrante por codigo
    @GetMapping("/{codigo}")
    public Integrante getIntegrante(@PathVariable String codigo) {
        return integranteService.getIntegrante(codigo);
    }

    // Registrar un integrante
    @PostMapping
    public Integrante registrarIntegrante(@RequestBody @Valid IntegranteRequest integrante) {
        Integrante integranteNuevo = integranteService.registrarIntegrante(new Integrante(integrante));
        return integranteNuevo;
    }

    // actualizar un integrante
    @PutMapping("/{codigo}")
    public Integrante actualizarIntegrante(@PathVariable String codigo,
            @RequestBody @Valid IntegranteRequest integrante) {
        return integranteService.actualizarIntegrante(codigo, new Integrante(integrante));
    }

    // Eliminar un integrante
    @DeleteMapping("/{codigo}")
    public MensajeResponse eliminarIntegrante(@PathVariable String codigo) {
        integranteService.eliminarIntegrante(codigo);
        return new MensajeResponse("El integrante con el codigo " + codigo + " fue eliminado");
    }

}
