package ingemedia.proyectos.aula.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    @GetMapping()
    public String getIntegrantes()
    {
        return "Prueba: integrantes de los proyectos";
    }
    
}
