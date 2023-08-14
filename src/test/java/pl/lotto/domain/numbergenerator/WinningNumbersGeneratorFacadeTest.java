package pl.lotto.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WinningNumbersGeneratorFacadeTest {
    private final WinningNumbersRepository winningNumbersRepository = new WinningNumbersRepositoryTestImpl();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);

    @Test
    public void it_should_return_set_of_required_size() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = numbersGeneratorFacade.generateWinningNumbers();
        //then
        assertThat(generatedNumbers.getWinningNumbers().size()).isEqualTo(6);
    }

    @Test
    public void it_should_return_set_of_required_size_within_required_range() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = numbersGeneratorFacade.generateWinningNumbers();
        //then
        int lowerBand = 1;
        int upperBand = 99;
        assertThat(generatedNumbers.getWinningNumbers().stream().allMatch(number -> number >= lowerBand && number <= upperBand)).isTrue();
    }

    @Test
    public void it_should_throw_an_exception_when_number_not_in_range() {
        //given
        Set<Integer> numbersOutOfRange = Set.of(1, 2, 3, 4, 5, 100);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl(numbersOutOfRange);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        //then
        assertThrows(IllegalStateException.class, numbersGeneratorFacade::generateWinningNumbers, "Number out of range!");
    }

    @Test
    public void it_should_return_collection_of_unique_values() {
        //given
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = numbersGeneratorFacade.generateWinningNumbers();
        //then
        int generatedNumbersSize = new HashSet<>(generatedNumbers.getWinningNumbers()).size();
        assertThat(generatedNumbersSize).isEqualTo(6);
    }

    @Test
    public void it_should_return_winning_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 7, 28, 12, 0, 0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .winningNumbers(generatedWinningNumbers)
                .date(drawDate)
                .build();
        winningNumbersRepository.save(winningNumbers);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = numbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate);
        //then
        WinningNumbersDto expectedWinningNumbersDto = WinningNumbersDto.builder()
                .date(drawDate)
                .winningNumbers(generatedWinningNumbers)
                .build();
        assertThat(expectedWinningNumbersDto).isEqualTo(winningNumbersDto);
    }

    @Test
    public void it_should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 7, 28, 12, 0, 0);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, () -> numbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate), "Not Found");
    }

    @Test
    public void it_should_return_true_if_numbers_are_generated_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 7, 28, 12, 0, 0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .winningNumbers(generatedWinningNumbers)
                .date(drawDate)
                .build();
        winningNumbersRepository.save(winningNumbers);
        RandomNumberGenerable generator = new WinningNumbersGeneratorTestImpl();
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade numbersGeneratorFacade = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        boolean areWinningNumbersGeneratedByDate = numbersGeneratorFacade.areWinningNumbersGeneratedByDate();
        //then
        assertTrue(areWinningNumbersGeneratedByDate);
    }
}