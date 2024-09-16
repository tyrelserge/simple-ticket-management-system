package io.ennov.simple_ticket_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.model.TicketDto;

@Service
public interface TicketService {

    List<Ticket> getUserTickets(Integer userId);

    List<Ticket> getTickets();

    Ticket getTicket(Integer ticketId);

    Ticket saveTicket(TicketDto ticketData);

    Ticket updateTicket(Integer ticketId, TicketDto ticketData);

    Ticket assignUserOnTicket(Integer ticketId, Integer useId);

    void deleteTicket(Integer ticketId);
    
}
