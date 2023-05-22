package ingemedia.proyectos.aula.services;

import ingemedia.proyectos.aula.entities.Integrante;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.IntegranteRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class IntegranteService {

  private final IntegranteRepository integranteRepository;

  IntegranteService(IntegranteRepository integranteRepository) {
    this.integranteRepository = integranteRepository;
  }

  // optener todos los integrantes
  public List<Integrante> getIntegrantes() {
    return integranteRepository.findAll();

  }

  // optener un integrante por codigo
  public Integrante getIntegrante(String codigo) {
    Optional<Integrante> integrante = integranteRepository.findByCodigo(codigo);
    if (integrante.isPresent()) {
      return integrante.get();
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }

  }

  // Registrar un integrante
  public Integrante registrarIntegrante(Integrante integrante) {

    // buscar por codigo
    Optional<Integrante> integranteExistente2 = integranteRepository.findByCodigo(integrante.getCodigo());
    if (integranteExistente2.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El integrante con el codigo " + integrante.getCodigo() + " ya existe"));
    }

    Optional<Integrante> integranteExistente = integranteRepository.findByCorreo(integrante.getCorreo());
    if (integranteExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El integrante con el correo " + integrante.getCorreo() + " ya existe"));
    } else {
      return integranteRepository.save(integrante);
    }
  }

  // Eliminar un integrante
  public void eliminarIntegrante(String codigo) {
    Optional<Integrante> integrante = integranteRepository.findByCodigo(codigo);
    if (integrante.isPresent()) {
      integranteRepository.deleteByCodigo(codigo);
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }
  }

  // Actualizar un integrante
  public Integrante actualizarIntegrante(String codigo, Integrante integrante) {
    Optional<Integrante> integranteExistente = integranteRepository.findByCodigo(codigo);
    if (integranteExistente.isPresent()) {
      integrante.setCodigo(codigo);
      return integranteRepository.save(integrante);
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }
  }
}
