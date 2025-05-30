package gs.beSafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CuidadosVulneraveisController {

    @GetMapping("/publico-especifico")
    public String cuidadosVulneraveis () {
        return "cuidados-grupos-vulneraveis";
    }
}
