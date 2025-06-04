package gs.repository;

import gs.model.LogCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogCadastroRepository extends JpaRepository<LogCadastro, Long> {
}
