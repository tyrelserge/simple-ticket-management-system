package io.ennov.simple_ticket_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.exception.ResourceNotFoundException;
import io.ennov.simple_ticket_management_system.model.TicketDto;
import io.ennov.simple_ticket_management_system.repository.TicketRepository;
import io.ennov.simple_ticket_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public List<Ticket> getUserTickets(Integer userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicket(Integer ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }

    @Override
    public Ticket saveTicket(TicketDto ticketData) {

        // Optional<User> optional =  userRepository.findById(ticketData.getUserId());

        // if(!optional.isPresent()) 
        //     throw new ResourceNotFoundException("User not found");

        Ticket ticket = new Ticket();
        ticket.setUserId(ticketData.getUserId());
        ticket.setTitle(ticketData.getTitle());
        ticket.setDescription(ticketData.getDescription());
        ticket.setStatus(ticketData.getStatus());

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Integer ticketId, TicketDto ticketData) {
        
        Optional<Ticket> optional = ticketRepository.findById(ticketId);

        if(!optional.isPresent()) 
            throw new ResourceNotFoundException("Ticket not found");

        Ticket ticket = optional.get();
        ticket.setTitle(ticketData.getTitle());
        ticket.setDescription(ticketData.getDescription());
        ticket.setStatus(ticketData.getStatus());
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket assignUserOnTicket(Integer ticketId, Integer useId) {

        Optional<User> optuser =  userRepository.findById(useId);

        if(!optuser.isPresent()) 
            throw new ResourceNotFoundException("User not found");

        Optional<Ticket> optTicket = ticketRepository.findById(ticketId);

        if(!optTicket.isPresent()) 
            throw new ResourceNotFoundException("Ticket not found");

        Ticket ticket = optTicket.get();
        ticket.setUserId(useId);
        return ticketRepository.save(ticket);

    }

    @Override
    public void deleteTicket(Integer ticketId) {
        Optional<Ticket> optional = ticketRepository.findById(ticketId);
        if(!optional.isPresent()) 
            throw new ResourceNotFoundException("Ticket not found");
        
        ticketRepository.deleteById(ticketId);
    }
    
}
