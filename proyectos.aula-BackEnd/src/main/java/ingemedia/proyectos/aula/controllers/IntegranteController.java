package ingemedia.proyectos.aula.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ingemedia.proyectos.aula.entities.Integrante;
import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.services.IntegranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/integrantes")
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
    public Integrante getIntegrante(@PathVariable int codigo) {
        return integranteService.getIntegrante(codigo);
    }

    // Registrar un integrante
    @PostMapping
    public Integrante registrarIntegrante(@RequestBody @Valid IntegranteRequest integrante) {
        return integranteService.registrarIntegrante(new Integrante(integrante));
    }

    // actualizar un integrante
    @PutMapping("/{codigo}")
    public Integrante actualizarIntegrante(@PathVariable int codigo, @RequestBody @Valid Integrante integrante) {
        return integranteService.actualizarIntegrante(codigo, integrante);
    }

    // Eliminar un integrante
    @DeleteMapping("/{codigo}")
    public void eliminarIntegrante(@PathVariable int codigo) {
        integranteService.eliminarIntegrante(codigo);
    }

}
