package io.ennov.simple_ticket_management_system.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getUserTickets(Integer userId) {
        return ticketRepository.findByUserId(userId);
    }
    
}
