package br.com.agenda.dto.response;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.agenda.domain.ErrorType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -3223902939764681664L;

    private final ErrorType errorType;
    private final String message;
    private final Map<String, String> details;
}
