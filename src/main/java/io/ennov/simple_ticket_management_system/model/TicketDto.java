package io.ennov.simple_ticket_management_system.model;

import lombok.Data;

@Data
public class TicketDto {
    private Integer userId;
    private String title;
    private String description;
    private String status;
}
