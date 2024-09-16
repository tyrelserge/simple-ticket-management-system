package io.ennov.simple_ticket_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ennov.simple_ticket_management_system.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    
}
