package ingemedia.proyectos.aula.services;

import java.util.List;
import java.util.Optional;



import org.springframework.stereotype.Service;

import ingemedia.proyectos.aula.entities.Proyecto;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.ProyectoRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

  ProyectoService(ProyectoRepository proyectoRepository) {
    this.proyectoRepository = proyectoRepository;
  }

  // optener todos los proyectos
  public List<Proyecto> getProyectos() {
    return proyectoRepository.findAll();

  }

  // optener un proyecto por id
  public Proyecto getProyecto(int id) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(id);
    if (proyecto.isPresent()) {
      return proyecto.get();
    } else {
      throw new BadRequestException(new ErrorResponse("El proyecto con id " + id + " no existe"));
    }

  }

  // Registrar un proyecto
  public Proyecto registrarProyecto(Proyecto proyecto) {
    Optional<Proyecto> proyectoExistente = proyectoRepository.findById(proyecto.getId());
    if (proyectoExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El proyecto con id " + proyecto.getId() + " ya existe"));
    } else {
      return proyectoRepository.save(proyecto);
    }
    }

  // CONTINUAR ACA!!!!!
  // Eliminar un proyecto
  public void eliminarProyecto(int id) {
    Optional<Proyecto> proyecto = proyectoRepository.findById(id);
    if (proyecto.isPresent()) {
      proyectoRepository.deleteById(id);
    } else {
      throw new BadRequestException(new ErrorResponse("El Proyecto con id " + id + " no existe"));
    }
  }

 

  // Actualizar un proyecto
  public Proyecto actualizarProyecto(int id, Proyecto proyecto) {
    Optional<Proyecto> proyectoExistente = proyectoRepository.findById(id);
    if (proyectoExistente.isPresent()) {
      proyecto.setId(id);
      return proyectoRepository.save(proyecto);
    } else {
      throw new BadRequestException(new ErrorResponse("El Proyecto con id " + id + " no existe"));
    }
  }
    
}

