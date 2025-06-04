package gs.service;


import gs.dto.UserCreateDTO;
import gs.model.LogCadastro;
import gs.repository.LogCadastroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogCadastroService {

    private final LogCadastroRepository logCadastroRepository;

    public LogCadastroService(LogCadastroRepository logCadastroRepository) {
        this.logCadastroRepository = logCadastroRepository;
    }

    public LogCadastro salvarLogCadastro (UserCreateDTO userCreateDTO) {
        LogCadastro logCadastro = new LogCadastro();
        logCadastro.setNome(userCreateDTO.getNome());
        logCadastro.setEmail(userCreateDTO.getEmail());
        logCadastro.setDataDeCadastro(LocalDateTime.now());
        return logCadastroRepository.save(logCadastro);
    }
}
