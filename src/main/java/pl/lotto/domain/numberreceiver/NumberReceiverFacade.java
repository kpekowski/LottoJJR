package pl.lotto.domain.numberreceiver;


import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.InputNumberResultsDto;

import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {
    private NumberValidator validator;

    public InputNumberResultsDto input(Set<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange)
            return InputNumberResultsDto.builder().message("success").build();
        return InputNumberResultsDto.builder().message("failed").build();
    }
}
