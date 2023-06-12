package ingemedia.proyectos.aula.services;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import ingemedia.proyectos.aula.entities.Materia;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.MateriaRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;

@Service
public class MateriaService {

  private final MateriaRepository materiaRepository;

  MateriaService(MateriaRepository materiaRepository) {
    this.materiaRepository = materiaRepository;
  }

  public List<Materia> getMaterias() {
    return materiaRepository.findAll();
  }

  public Materia registrarMateria(Materia materia) {
    Optional<Materia> materiaExistente = materiaRepository.findByNombre(materia.getNombre());
    if (materiaExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("La materia con el nombre " + materia.getNombre() + " ya existe"));
    }
    return materiaRepository.save(materia);
  }

  // eliminar una materia
  public void eliminarMateria(Long id) {
    Optional<Materia> materiaExistente = materiaRepository.findById(id);
    if (materiaExistente.isPresent()) {
      materiaRepository.deleteById(id);
    } else {
      throw new BadRequestException(new ErrorResponse("La materia con el id " + id + " no existe"));
    }
  }

}
