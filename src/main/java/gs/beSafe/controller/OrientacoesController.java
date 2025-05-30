package gs.beSafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrientacoesController {

    @GetMapping("/cuidados-pos-desastre")
        public String orientacoes () {
            return "orientacoes-pos-enchente";
        }
    }


