package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.request.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {

  @Id
  private String codigo;

  @NotNull
  @NotBlank
  private String nombre;

  @NotNull
  @NotBlank
  private String apellido;

  @NotNull
  @NotBlank
  @Email
  private String correo;

  @NotNull
  @NotBlank
  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Rol rol;

  @OneToMany(orphanRemoval = true, mappedBy = "integrante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<IntegranteProyecto> proyectos = new ArrayList<>();

  public Usuario(IntegranteRequest integranteRequest) {
    this.codigo = integranteRequest.getCodigo();
    this.nombre = integranteRequest.getNombre();
    this.apellido = integranteRequest.getApellido();
    this.correo = integranteRequest.getCorreo();
    this.rol = integranteRequest.getRol();
    this.password = integranteRequest.getPassword();
  }

  public Usuario(String codigo, String nombre, String appellido, Rol rol) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.apellido = appellido;
    this.rol = rol;
  }

  // agregar proyectos
  public void addProyecto(IntegranteProyecto proyecto) {
    this.proyectos.add(proyecto);
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.rol.name()));
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return correo;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  // crear el metodo toString
  public String toString() {
    return "Usuario{" +
        "codigo=" + codigo +
        ", nombre=" + nombre +
        ", apellido=" + apellido +
        ", correo=" + correo +
        ", rol=" + rol +
        "}";
  }
}
