package io.ennov.simple_ticket_management_system.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ennov.simple_ticket_management_system.Constants;
import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.model.ResponseDto;
import io.ennov.simple_ticket_management_system.model.TicketDto;
import io.ennov.simple_ticket_management_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    
    @GetMapping("")
    public ResponseEntity<ResponseDto<List<Ticket>>> getTickets() {
        List<Ticket> tickets = ticketService.getTickets();
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, tickets));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<ResponseDto<Ticket>> getTicket(@PathVariable("ticketId") Integer ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        return ticket==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) : 
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, ticket));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<Ticket>> saveTicket(@RequestBody TicketDto ticketData) {
        Ticket ticket = ticketService.saveTicket(ticketData);
        return ticket==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) : 
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, ticket));
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<ResponseDto<Ticket>> updateTicket(@PathVariable("ticketId") Integer ticketId, @RequestBody TicketDto ticketData) {
        Ticket ticket = ticketService.updateTicket(ticketId, ticketData);
        return ticket==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) : 
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, ticket));
    }

    @PutMapping("/{ticketId}/assign/{useId}")
    public ResponseEntity<ResponseDto<Ticket>> updateTicket(@PathVariable("ticketId") Integer ticketId, @PathVariable("useId") Integer useId) {
        Ticket ticket = ticketService.assignUserOnTicket(ticketId, useId);
        return ticket==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) : 
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, ticket));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ResponseDto<String>> delTicket(@PathVariable("ticketId") Integer ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, "Ticket deleted"));
    }
    
}
