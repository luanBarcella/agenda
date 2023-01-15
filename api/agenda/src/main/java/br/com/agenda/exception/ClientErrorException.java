package br.com.agenda.exception;

import br.com.agenda.domain.ErrorType;

public class ClientErrorException extends AbstractErrorException {

    private static final long serialVersionUID = 1486297407038116871L;

    private final ErrorType errorType;

    public ClientErrorException(final ErrorType errorType, final String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public ClientErrorException(final ErrorType errorType, final String msg, final Throwable th) {
        super(msg, th);
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

}
