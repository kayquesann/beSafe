package gs.beSafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrevencaoController {

    @GetMapping("/preparacao")
    public String prevencao () {
        return "tela-prevencao";
    }
}
