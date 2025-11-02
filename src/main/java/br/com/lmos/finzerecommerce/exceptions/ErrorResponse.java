package br.com.lmos.finzerecommerce.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
}
