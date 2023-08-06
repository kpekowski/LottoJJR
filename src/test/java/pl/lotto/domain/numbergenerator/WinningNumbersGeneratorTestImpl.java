package pl.lotto.domain.numbergenerator;

import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumbersGenerable {
    private final Set<Integer> generatedNumbers;

    WinningNumbersGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    WinningNumbersGeneratorTestImpl(Set<Integer> winningNumbers){
        this.generatedNumbers = winningNumbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}
