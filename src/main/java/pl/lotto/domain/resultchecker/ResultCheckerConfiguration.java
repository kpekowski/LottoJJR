package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

@Configuration
public class ResultCheckerConfiguration {
    @Bean
    ResultCheckerFacade resultCheckerFacade(WinningNumbersGeneratorFacade generatorFacade, NumberReceiverFacade receiverFacade, PlayerRepository playerRepository) {
        WinnerRetriever winnerRetriever = new WinnerRetriever();
        return new ResultCheckerFacade(generatorFacade, receiverFacade, playerRepository, winnerRetriever);
    }
}
