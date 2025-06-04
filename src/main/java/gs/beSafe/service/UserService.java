package gs.beSafe.service;

import gs.beSafe.config.RabbitConfig;
import gs.beSafe.dto.UserCreateDTO;
import gs.beSafe.dto.UserResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import gs.beSafe.repository.RoleRepository;
import gs.beSafe.repository.UserRepository;
import gs.beSafe.model.Role;
import gs.beSafe.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final RabbitTemplate rabbitTemplate;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), true,true,true, true, user.getAuthorities());
    }

    public UserResponseDTO converterEntityParaResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole().getRoleId()
        );
    }

    public User obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UserResponseDTO obterUsuarioLogadoDTO() {
        User user = obterUsuarioLogado();
        return converterEntityParaResponseDTO(user);
    }



    public User buscarUsuario (Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user;
    }

    public UserResponseDTO cadastrarUsuario(UserCreateDTO userCreateDTO) {
        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        Role role = roleRepository.findById(userCreateDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role não encontrada com o ID: " + userCreateDTO.getRoleId()));
        User user = new User();
        user.setNome(userCreateDTO.getNome());
        user.setEmail(userCreateDTO.getEmail());
        user.setSenha(passwordEncoder.encode(userCreateDTO.getSenha()));
        user.setRole(role);
        userRepository.save(user);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,RabbitConfig.ROUTING_KEY,userCreateDTO);
        return converterEntityParaResponseDTO(user);
    }

    public UserResponseDTO atualizarEmail(String email) {
        User user = obterUsuarioLogado();

        if (!user.getEmail().equals(email)) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("E-mail já cadastrado.");
            }
        }

        user.setEmail(email);
        userRepository.save(user);
        return converterEntityParaResponseDTO(user);
    }


    public UserResponseDTO atualizarSenha(String novaSenha) {
        User user = obterUsuarioLogado();
        user.setSenha(passwordEncoder.encode(novaSenha));
        userRepository.save(user);
        return converterEntityParaResponseDTO(user);
    }


    public void deletarUsuario () {
        User user = obterUsuarioLogado();
        userRepository.delete(user);
    }

    public List<UserResponseDTO> listarUsuarios () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::converterEntityParaResponseDTO)
                .collect(Collectors.toList());
    }

}
