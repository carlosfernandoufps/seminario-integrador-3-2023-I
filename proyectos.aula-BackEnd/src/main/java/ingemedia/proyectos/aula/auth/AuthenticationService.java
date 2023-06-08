package ingemedia.proyectos.aula.auth;

import ingemedia.proyectos.aula.config.JwtService;
import ingemedia.proyectos.aula.request.Rol;
//import com.example.parqueaderoapi.user.User;
import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.exceptions.BadRequestException;
//import com.example.parqueaderoapi.user.UserRepository;
import ingemedia.proyectos.aula.repositories.UsuarioRepository;
import ingemedia.proyectos.aula.responses.ErrorResponse;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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

  public AuthenticationResponse register(RegisterRequest request) {
    var user = Usuario.builder()
        .nombre(request.getNombre())
        .apellido(request.getApellido())
        .correo(request.getCorreo())
        .password(passwordEncoder.encode(request.getPassword()))
        .rol(Rol.ADMIN)
        .build();
    repository.save(user);
    // var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
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

    // Usuario user = usuario.get();
    var jwtToken = jwtService.generateToken(user);
    // revokeAllUserTokens(user);
    // saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();

  }

}
