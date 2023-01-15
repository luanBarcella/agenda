package br.com.agenda.interceptor;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.agenda.domain.ErrorType;
import br.com.agenda.domain.Message;
import br.com.agenda.dto.response.ErrorResponse;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerErrorInterceptorTest {

    @InjectMocks
    private ControllerErrorInterceptor interceptor;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Handle ClientErrorException Sem Detalhes")
    public void HandleClientErrorExceptionSemDetalhes(){
        final ErrorType errorType = ErrorType.VALIDATION;
        final String message = "Mensagem teste erro.";
        final ClientErrorException exception = new ClientErrorException(errorType, message);

        final ResponseEntity<ErrorResponse> response = interceptor.handleException(exception);

        assertNotNull(response);
        assertEquals(errorType.getHttpStatus(), response.getStatusCode());
        assertNotNull(response.getBody());
        Assertions.assertEquals(errorType, response.getBody().getErrorType());
        assertEquals(message, response.getBody().getMessage());
        assertTrue(isNull(response.getBody().getDetails()) || isEmpty(response.getBody().getDetails()));

    }

    @Test
    @DisplayName("Handle ClientErrorException Com Detalhes")
    public void HandleClientErrorExceptionComDetalhes(){
        final ErrorType errorType = ErrorType.VALIDATION;
        final String message = "Mensagem teste erro.";

        final Map<String, String> details = new HashMap<>();
        details.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        details.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        details.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        final ClientErrorException exception = new ClientErrorException(errorType, message);
        exception.getDetails().putAll(details);

        final ResponseEntity<ErrorResponse> response = interceptor.handleException(exception);

        assertNotNull(response);
        assertEquals(errorType.getHttpStatus(), response.getStatusCode());
        assertNotNull(response.getBody());
        Assertions.assertEquals(errorType, response.getBody().getErrorType());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(details, response.getBody().getDetails());

    }

    @Test
    @DisplayName("Handle MethodArgumentNotValidException Com Mensagem Padrão.")
    public void handleMethodArgumentNotValidExceptionComMensagemPadrao() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setContextPath("/teste");
        request.setServletPath("/padrao");

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity<ErrorResponse> response = interceptor.handleException(mock(MethodArgumentNotValidException.class));

        assertNotNull(response);
        assertNotNull(response.getBody());
        Assertions.assertEquals(ErrorType.VALIDATION.getHttpStatus(), response.getStatusCode());
        Assertions.assertEquals(ErrorType.VALIDATION, response.getBody().getErrorType());

        verify(messageService).get(Message.REQUEST_INVALIDA);

    }
}
