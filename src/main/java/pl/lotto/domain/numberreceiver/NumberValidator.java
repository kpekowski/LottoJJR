package pl.lotto.domain.numberreceiver;

import java.util.Set;

class NumberValidator {
    boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .filter(number -> number <= 99)
                .filter(number -> number >= 1)
                .count() == 6;
    }
}
