package pl.lotto.domain.numberreceiver;


import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.lotto.domain.numberreceiver.ValidationResult.INPUT_SUCCESS;

@AllArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator validator;
    private final DrawDateGenerator drawDateGenerator;
    private final HashGenerable hashGenerator;
    private final TicketRepository repository;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {
        List<ValidationResult> validationResultList = validator.validate(numbersFromUser);
        if (!validationResultList.isEmpty()) {
            String resultMessage = validator.createResultMessage();
            return new NumberReceiverResponseDto(null, resultMessage);
        }
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        String hash = hashGenerator.getHash();

        TicketDto generatedTicket = TicketDto.builder()
                .hash(hash)
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();

        Ticket savedTicket = Ticket.builder()
                .hash(hash)
                .numbers(generatedTicket.numbers())
                .drawDate(generatedTicket.drawDate())
                .build();

        repository.save(savedTicket);

        return new NumberReceiverResponseDto(generatedTicket, INPUT_SUCCESS.info);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date) {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        if (date.isAfter(nextDrawDate))
            return Collections.EMPTY_LIST;
        return repository.findAllByDrawDate(date)
                .stream()
                .filter(ticket -> ticket.drawDate().equals(date))
                .map(ticket -> TicketDto.builder()
                        .hash(ticket.hash())
                        .numbers(ticket.numbers())
                        .drawDate(ticket.drawDate())
                        .build())
                .collect(Collectors.toList());
    }

    public LocalDateTime retrieveNextDrawDate() {
        return drawDateGenerator.getNextDrawDate();
    }

    public TicketDto findByHash(String hash) {
        Ticket ticket = repository.findByHash(hash);
        return TicketDto.builder()
                .hash(hash)
                .numbers(ticket.numbers())
                .drawDate(ticket.drawDate())
                .build();
    }
}
