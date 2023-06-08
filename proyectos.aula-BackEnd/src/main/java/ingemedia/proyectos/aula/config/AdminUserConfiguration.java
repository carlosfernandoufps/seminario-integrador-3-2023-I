package ingemedia.proyectos.aula.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ingemedia.proyectos.aula.entities.Usuario;
import ingemedia.proyectos.aula.repositories.UsuarioRepository;
import ingemedia.proyectos.aula.request.Rol;

@Configuration
public class AdminUserConfiguration {

  @Bean
  public CommandLineRunner initAdminUser(UsuarioRepository userRepository) {
    return args -> {
      // Crear usuario administrador
      // Verificar si ya existe un usuario con el correo "admin@mail.com"
      if (!userRepository.existsByCorreo("admin@mail.com")) {

        Usuario admin = new Usuario();
        admin.setCodigo("1111111");
        admin.setNombre("admin");
        admin.setApellido("admin");
        admin.setCorreo("admin@mail.com");
        admin.setPassword("admin");
        admin.setRol(Rol.ADMIN);

        // Codificar la contraseña
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Guardar el usuario en la base de datos
        // Si el usuario ya existe, no se vuelve a crear
        // if (!userRepository.findByCorreo(admin.getCorreo()).isEmpty())
        userRepository.save(admin);
      }
    };
  }
}
