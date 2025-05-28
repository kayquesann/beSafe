package gs.beSafe.service;

import dto.UserCreateDTO;
import dto.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.RoleRepository;
import repository.UserRepository;
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

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), true,true,true, true, user.getAuthorities());
    }

    private UserResponseDTO converterEntityParaResponseDTO (User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole().getRoleId()
        );
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
        return converterEntityParaResponseDTO(user);
    }

    public UserResponseDTO atualizarEmail(Long id, String email) {
        User user = buscarUsuario(id);
        user.setEmail(email);
        userRepository.save(user);
        return converterEntityParaResponseDTO(user);
    }

    public UserResponseDTO atualizarSenha(Long id, String senha) {
        User user = buscarUsuario(id);
        user.setSenha(passwordEncoder.encode(senha));
        userRepository.save(user);
        return converterEntityParaResponseDTO(user);
    }


    public void deletarUsuario (Long id) {
        User user = buscarUsuario(id);
        userRepository.delete(user);
    }

    public List<UserResponseDTO> listarUsuarios () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::converterEntityParaResponseDTO)
                .collect(Collectors.toList());
    }

}
