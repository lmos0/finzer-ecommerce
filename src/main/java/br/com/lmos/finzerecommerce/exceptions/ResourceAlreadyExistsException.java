package br.com.lmos.finzerecommerce.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resource, String field, Object value) {
        super(String.format("%s já existe com %s: '%s'", resource, field, value));
    }
}
