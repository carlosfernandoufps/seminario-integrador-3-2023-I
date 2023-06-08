package ingemedia.proyectos.aula.controllers;

import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import ingemedia.proyectos.aula.services.IntegranteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/integrantes")
public class UsuarioController {

    private final IntegranteService integranteService;

    UsuarioController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    // obtener todos los integrantes
    @GetMapping
    public List<Usuario> getIntegrantes() {
        return integranteService.getIntegrantes();
    }

    // obtener un integrante por codigo
    @GetMapping("/{codigo}")
    public Usuario getIntegrante(@PathVariable String codigo) {
        return integranteService.getIntegrante(codigo);
    }

    // Registrar un integrante
    @PostMapping
    public Usuario registrarIntegrante(@RequestBody @Valid IntegranteRequest integrante) {
        Usuario integranteNuevo = integranteService.registrarIntegrante(new Usuario(integrante));
        return integranteNuevo;
    }

    // actualizar un integrante
    @PutMapping("/{codigo}")
    public Usuario actualizarIntegrante(@PathVariable String codigo,
            @RequestBody @Valid IntegranteRequest integrante) {
        return integranteService.actualizarIntegrante(codigo, new Usuario(integrante));
    }

    // Eliminar un integrante
    @DeleteMapping("/{codigo}")
    public MensajeResponse eliminarIntegrante(@PathVariable String codigo) {
        integranteService.eliminarIntegrante(codigo);
        return new MensajeResponse("El integrante con el codigo " + codigo + " fue eliminado");
    }

}
