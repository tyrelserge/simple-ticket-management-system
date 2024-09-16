package io.ennov.simple_ticket_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.ennov.simple_ticket_management_system.entities.Ticket;

@Service
public interface TicketService {

    List<Ticket> getUserTickets(Integer userId);
    
}
