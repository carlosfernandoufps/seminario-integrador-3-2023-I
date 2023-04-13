package ingemedia.proyectos.aula.entities;

import ingemedia.proyectos.aula.request.IntegranteRequest;
import ingemedia.proyectos.aula.request.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Integrante")
public class Integrante {

    @Id
    @NotNull
    private int codigo;

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

    public Integrante(IntegranteRequest integranteRequest) {
        this.codigo = integranteRequest.getCodigo();
        this.nombre = integranteRequest.getNombre();
        this.apellido = integranteRequest.getApellido();
        this.correo = integranteRequest.getCorreo();
        this.rol = integranteRequest.getRol();
    }
}
