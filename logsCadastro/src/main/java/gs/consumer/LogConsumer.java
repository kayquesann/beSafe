package gs.consumer;

import gs.dto.UserCreateDTO;
import gs.service.LogCadastroService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {

    private static final String QUEUE_NAME = "cadastro.concluido.queue";
    private final LogCadastroService logCadastroService;

    public LogConsumer(LogCadastroService logCadastroService) {
        this.logCadastroService = logCadastroService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void salvarLogCadastro(UserCreateDTO userCreateDTO) {
        logCadastroService.salvarLogCadastro(userCreateDTO);
    }
}
