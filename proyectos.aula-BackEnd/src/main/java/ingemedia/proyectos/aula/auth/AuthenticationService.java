package ingemedia.proyectos.aula.auth;

import ingemedia.proyectos.aula.config.JwtService;
import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.request.Rol;
//import com.example.parqueaderoapi.user.User;
import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
//import com.example.parqueaderoapi.user.UserRepository;
import ingemedia.proyectos.aula.repositories.UsuarioRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsuarioRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(IntegranteRequest request) {

    if (request.getRol() == Rol.ADMIN) {
      throw new BadRequestException(
          ErrorResponse.builder()
              // .codigo("400")
              .message("No se puede Registrar un Admin")
              .build());
    }
    Optional<Usuario> usuario = repository.findByCorreo(request.getCorreo());
    Optional<Usuario> usuario2 = repository.findByCodigo(request.getCodigo());
    if (usuario2.isPresent()) {
      throw new BadRequestException(
          ErrorResponse.builder()
              // .codigo("400")
              .message("El codigo ya esta registrado")
              .build());
    }
    if (usuario.isPresent()) {
      throw new BadRequestException(
          ErrorResponse.builder()
              // .codigo("400")
              .message("El correo ya se encuentra registrado")
              .build());
    }
    var user = Usuario.builder()
        .codigo(request.getCodigo())
        .nombre(request.getNombre())
        .apellido(request.getApellido())
        .correo(request.getCorreo())
        .password(passwordEncoder.encode(request.getPassword()))
        .rol(request.getRol())
        .build();
    repository.save(user);
    // var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user, user.getCodigo(), user.getNombre(), user.getApellido(),
        user.getCorreo());
    // saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getCorreo(),
              request.getPassword()));
    } catch (AuthenticationException e) {
      // System.out.println("credenciales invalidas");
      String mensaje = e.getMessage();

      throw new BadRequestException(new ErrorResponse("credenciales incorrectas: " + mensaje));
    }
    var user = repository.findByCorreo(request.getCorreo())
        .orElseThrow();
    System.out.println("user: " + user);

    // Usuario user = usuario.get();
    var jwtToken = jwtService.generateToken(user, user.getCodigo(), user.getNombre(), user.getApellido(),
        user.getCorreo());
    // revokeAllUserTokens(user);
    // saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();

  }

  // cerrar sesion

  public void logout(HttpServletRequest request) {
    // invalidar la sesion actual
    request.getSession().invalidate();
  }

}
