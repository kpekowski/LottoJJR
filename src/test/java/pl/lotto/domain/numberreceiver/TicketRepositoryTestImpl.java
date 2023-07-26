package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TicketRepositoryTestImpl implements TicketRepository {
    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        inMemoryDatabase.put(ticket.hash(),ticket);
        return ticket;
    }

    @Override
    public Collection<Ticket> findAllTicketsByDrawDate(LocalDateTime date) {
        return inMemoryDatabase.values()
                .stream()
                .filter(ticket -> ticket.drawDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public Ticket findByHash(String hash) {
        return inMemoryDatabase.get(hash);
    }
}
