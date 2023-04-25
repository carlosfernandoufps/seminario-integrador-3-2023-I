package ingemedia.proyectos.aula.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "integrante")
public class Integrante {

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
    @Enumerated(EnumType.STRING)
    private Rol rol;

    // @ManyToMany(mappedBy = "integrantes")
    @ManyToMany(mappedBy = "integrantes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Proyecto> proyectos = new ArrayList<>();

    public Integrante(IntegranteRequest integranteRequest) {
        this.codigo = integranteRequest.getCodigo();
        this.nombre = integranteRequest.getNombre();
        this.apellido = integranteRequest.getApellido();
        this.correo = integranteRequest.getCorreo();
        this.rol = integranteRequest.getRol();
    }

    // agregar proyectos
    public void addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
    }
}
