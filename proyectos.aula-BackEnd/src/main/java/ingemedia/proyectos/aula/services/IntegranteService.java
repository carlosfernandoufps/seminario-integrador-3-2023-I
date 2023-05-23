package ingemedia.proyectos.aula.services;

import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.repositories.UsuarioRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class IntegranteService {

  private final UsuarioRepository integranteRepository;

  IntegranteService(UsuarioRepository integranteRepository) {
    this.integranteRepository = integranteRepository;
  }

  // optener todos los integrantes
  public List<Usuario> getIntegrantes() {
    return integranteRepository.findAll();

  }

  // optener un integrante por codigo
  public Usuario getIntegrante(String codigo) {
    Optional<Usuario> integrante = integranteRepository.findByCodigo(codigo);
    if (integrante.isPresent()) {
      return integrante.get();
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }

  }

  // Registrar un integrante
  public Usuario registrarIntegrante(Usuario integrante) {

    // buscar por codigo
    Optional<Usuario> integranteExistente2 = integranteRepository.findByCodigo(integrante.getCodigo());
    if (integranteExistente2.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El integrante con el codigo " + integrante.getCodigo() + " ya existe"));
    }

    Optional<Usuario> integranteExistente = integranteRepository.findByCorreo(integrante.getCorreo());
    if (integranteExistente.isPresent()) {
      throw new BadRequestException(
          new ErrorResponse("El integrante con el correo " + integrante.getCorreo() + " ya existe"));
    } else {
      return integranteRepository.save(integrante);
    }
  }

  // Eliminar un integrante
  public void eliminarIntegrante(String codigo) {
    Optional<Usuario> integrante = integranteRepository.findByCodigo(codigo);
    if (integrante.isPresent()) {
      integranteRepository.deleteByCodigo(codigo);
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }
  }

  // Actualizar un integrante
  public Usuario actualizarIntegrante(String codigo, Usuario integrante) {
    Optional<Usuario> integranteExistente = integranteRepository.findByCodigo(codigo);
    if (integranteExistente.isPresent()) {
      integrante.setCodigo(codigo);
      return integranteRepository.save(integrante);
    } else {
      throw new BadRequestException(new ErrorResponse("El integrante con el codigo " + codigo + " no existe"));
    }
  }
}
