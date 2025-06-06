package gs.beSafe.service;

import gs.beSafe.config.RabbitConfig;
import gs.beSafe.dto.UserCreateDTO;
import gs.beSafe.dto.UserResponseDTO;
import gs.beSafe.model.Role;
import gs.beSafe.model.User;
import gs.beSafe.repository.RoleRepository;
import gs.beSafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarUsuario_comDadosValidos_deveRetornarUserResponseDTO() {
        // Arrange
        UserCreateDTO dto = mock(UserCreateDTO.class);
        when(dto.getEmail()).thenReturn("test@example.com");
        when(dto.getNome()).thenReturn("Test User");
        when(dto.getSenha()).thenReturn("123456");
        when(dto.getRoleId()).thenReturn(1);

        Role role = new Role();
        role.setRoleId(1);

        User userSalvo = new User();
        userSalvo.setId(1L);
        userSalvo.setNome("Test User");
        userSalvo.setEmail("test@example.com");
        userSalvo.setRole(role);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userSalvo);

        // Act
        UserResponseDTO response = userService.cadastrarUsuario(dto);

        // Assert
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getNome());
        verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, dto);
    }

    @Test
    void buscarUsuario_comIdExistente_deveRetornarUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.buscarUsuario(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void buscarUsuario_comIdInexistente_deveLancarExcecao() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.buscarUsuario(2L));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void listarUsuarios_deveRetornarListaDeUserResponseDTO() {
        User user1 = new User(); user1.setId(1L); user1.setNome("A"); user1.setEmail("a@email.com"); user1.setRole(new Role());
        User user2 = new User(); user2.setId(2L); user2.setNome("B"); user2.setEmail("b@email.com"); user2.setRole(new Role());

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> result = userService.listarUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void atualizarEmail_deveAlterarEmailComSucesso() {
        User user = new User();
        user.setEmail("antigo@email.com");

        Role role = new Role();
        role.setRoleId(1);
        user.setRole(role);

        // Simula obterUsuarioLogado()
        UserService spyService = Mockito.spy(userService);
        doReturn(user).when(spyService).obterUsuarioLogado();

        when(userRepository.findByEmail("novo@email.com")).thenReturn(Optional.empty());

        UserResponseDTO result = spyService.atualizarEmail("novo@email.com");

        assertEquals("novo@email.com", user.getEmail());
    }

    @Test
    void atualizarSenha_deveCodificarNovaSenha() {
        User user = new User();
        user.setSenha("antiga");

        Role role = new Role();
        role.setRoleId(1);
        user.setRole(role);

        UserService spyService = Mockito.spy(userService);
        doReturn(user).when(spyService).obterUsuarioLogado();

        when(passwordEncoder.encode("novaSenha")).thenReturn("senhaCodificada");

        UserResponseDTO result = spyService.atualizarSenha("novaSenha");

        assertEquals("senhaCodificada", user.getSenha());
    }

    @Test
    void deletarUsuario_deveChamarDeleteDoRepositorio() {
        User user = new User();
        user.setId(10L);

        UserService spyService = Mockito.spy(userService);
        doReturn(user).when(spyService).obterUsuarioLogado();

        spyService.deletarUsuario();

        verify(userRepository).delete(user);
    }

    @Test
    void atualizarEmail_quandoEmailJaExiste_deveLancarExcecao() {
        User user = new User();
        user.setEmail("antigo@email.com");

        UserService spyService = Mockito.spy(userService);
        doReturn(user).when(spyService).obterUsuarioLogado();

        when(userRepository.findByEmail("emailExistente@email.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> {
            spyService.atualizarEmail("emailExistente@email.com");
        });
    }


}
