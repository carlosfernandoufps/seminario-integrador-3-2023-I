package ingemedia.proyectos.aula.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.request.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
