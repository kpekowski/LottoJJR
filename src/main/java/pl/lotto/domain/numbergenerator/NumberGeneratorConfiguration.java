package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

public class NumberGeneratorConfiguration {

    WinningNumbersGeneratorFacade createForTest(RandomNumbersGenerable randomNumbersGenerable,
                                                WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade){
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumbersGenerable,winningNumbersValidator,winningNumbersRepository,numberReceiverFacade);
    }
}
