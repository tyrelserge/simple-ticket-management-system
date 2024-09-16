package io.ennov.simple_ticket_management_system.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundExceptionMessage {
    private String statusCode;
    private Date timestamp;
    private String message;
    private String source;
}
