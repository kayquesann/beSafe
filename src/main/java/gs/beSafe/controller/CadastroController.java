package gs.beSafe.controller;

import gs.beSafe.dto.UserCreateDTO;
import gs.beSafe.service.RoleService;
import gs.beSafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    private final UserService userService;
    private final RoleService roleService;

    public CadastroController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String formularioCadastro(Model model) {
        model.addAttribute("usuario", new UserCreateDTO());
        model.addAttribute("roles", roleService.listarTodasRoles());
        return "cadastro";
    }


    @PostMapping
    public String cadastrarUsuario(@ModelAttribute("usuario") UserCreateDTO userCreateDTO) {
        userService.cadastrarUsuario(userCreateDTO);
        return "redirect:/login";
    }

}