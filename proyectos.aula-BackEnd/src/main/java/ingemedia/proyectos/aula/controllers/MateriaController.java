package ingemedia.proyectos.aula.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ingemedia.proyectos.aula.entities.Materia;
import ingemedia.proyectos.aula.request.MateriaRequest;
import ingemedia.proyectos.aula.responses.MensajeResponse;
import ingemedia.proyectos.aula.services.MateriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/materias")
public class MateriaController {

  private final MateriaService materiaService;

  MateriaController(MateriaService materiaService) {
    this.materiaService = materiaService;
  }

  @GetMapping
  public List<Materia> getMaterias() {
    return materiaService.getMaterias();
  }

  @PostMapping
  public Materia registrarMateria(@RequestBody @Valid MateriaRequest materia) {
    System.out.println("Nombre de la materia: " + materia.getNombre());
    Materia materiaNueva = materiaService.registrarMateria(new Materia(materia));
    return materiaNueva;
  }

  @DeleteMapping("/{id}")
  public MensajeResponse eliminarMateria(@PathVariable Long id) {
    materiaService.eliminarMateria(id);
    return new MensajeResponse("La materia con el id " + id + " fue eliminada");
  }

}
