package gs.beSafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContatoController {

    @GetMapping("/contato")
    public String exibirContatos () {
        return "contato";
    }
}
