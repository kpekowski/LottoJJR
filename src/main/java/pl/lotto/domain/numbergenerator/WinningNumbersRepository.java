package pl.lotto.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WinningNumbersRepository extends MongoRepository<WinningNumbers, String> {
    Optional<WinningNumbers> findFirstByDate(LocalDateTime date);
    boolean existsByDate(LocalDateTime date);
}
