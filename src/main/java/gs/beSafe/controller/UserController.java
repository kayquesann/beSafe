package gs.beSafe.controller;

import gs.beSafe.dto.UserResponseDTO;
import gs.beSafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Página do perfil
    @GetMapping
    public String mostrarPerfil(Model model) {
        UserResponseDTO usuario = userService.obterUsuarioLogadoDTO();
        model.addAttribute("usuario", usuario);
        return "perfil"; // perfil.html
    }

    @GetMapping("/form-atualizar-email")
    public String formularioAtualizarEmail() {
        return "form-atualizar-email";
    }

    @GetMapping("/form-atualizar-senha")
    public String formularioAtualizarSenha() {
        return "form-atualizar-senha";
    }

    @PostMapping("/atualizar-email")
    public String atualizarEmail(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            userService.atualizarEmail(email);
            redirectAttributes.addFlashAttribute("sucesso", "Email atualizado com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/perfil";
    }

    @PostMapping("/atualizar-senha")
    public String atualizarSenha(@RequestParam String novaSenha, RedirectAttributes redirectAttributes) {
        try {
            userService.atualizarSenha(novaSenha);
            redirectAttributes.addFlashAttribute("sucesso", "Senha atualizada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/perfil";
    }

    @PostMapping("/deletar")
    public String deletarConta() {
        userService.deletarUsuario();
        return "redirect:/login";
    }
}