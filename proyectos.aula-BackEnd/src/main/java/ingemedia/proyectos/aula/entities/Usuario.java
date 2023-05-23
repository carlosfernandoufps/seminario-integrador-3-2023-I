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
    }

    // agregar proyectos
    public void addProyecto(IntegranteProyecto proyecto) {
        this.proyectos.add(proyecto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.rol.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
