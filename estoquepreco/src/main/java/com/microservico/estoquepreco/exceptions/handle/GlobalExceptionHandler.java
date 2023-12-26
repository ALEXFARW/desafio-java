package com.microservico.estoquepreco.exceptions.handle;

import com.microservico.estoquepreco.exceptions.BusinessException;
import com.microservico.estoquepreco.exceptions.ConflictException;
import com.microservico.estoquepreco.exceptions.InvalidParametersException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static java.lang.String.format;
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //@Autowired
    //private EmailService emailService;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException ex, HttpServletRequest request) {
        //emailService.enviaEmailExcecao(ex);
        return response(ex.getMessage(), request, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException ex, HttpServletRequest request) {
      //  emailService.enviaEmailExcecao(ex);
        return response(ex.getMessage(), request, HttpStatus.CONFLICT, LocalDateTime.now());
    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<ErrorResponse> handlerConflictException(InvalidParametersException ex, HttpServletRequest request) {
        //  emailService.enviaEmailExcecao(ex);
        return response(ex.getMessage(), request, HttpStatus.BAD_GATEWAY, LocalDateTime.now());
    }

    private ResponseEntity<ErrorResponse> response(final String message, final HttpServletRequest request,
                                                   final HttpStatus status, LocalDateTime data) {
        log.error(format("Error message: %s data: %s status: %s uri: %s %s",
                message, data, status.value(), request.getRequestURI(), status.getReasonPhrase()));

        return ResponseEntity
              .status(status)
              .body(new ErrorResponse(message, data, status.value() , request.getRequestURI(),status.getReasonPhrase()));
    }
}
