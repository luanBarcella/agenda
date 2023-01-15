package br.com.agenda.domain;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum ErrorType {

    VALIDATION(BAD_REQUEST);

    private HttpStatus httpStatus;
}
