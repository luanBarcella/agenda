package br.com.agenda.interceptor;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.agenda.domain.ErrorType;
import br.com.agenda.domain.Message;
import br.com.agenda.dto.response.ErrorResponse;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.exception.ClientErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerErrorInterceptor {

    private final MessageService messageService;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final ClientErrorException exception){
        ErrorResponse error = ErrorResponse.builder()
            .errorType(exception.getErrorType())
            .message(exception.getMessage())
            .details(exception.getDetails()).build();

        return new ResponseEntity<>(error, exception.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException exception){

        ErrorResponse error = ErrorResponse.builder()
            .errorType(ErrorType.VALIDATION)
            .message(messageService.get(Message.REQUEST_INVALIDA))
            .details(exception.getFieldErrors()
                .stream()
                .collect(
                    toMap(
                        FieldError::getField,
                        fieldError-> ofNullable(fieldError.getDefaultMessage()).orElse("")))
            ).build();

        return new ResponseEntity<>(error, ErrorType.VALIDATION.getHttpStatus());
    }
}
