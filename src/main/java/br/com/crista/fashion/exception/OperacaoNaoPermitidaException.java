package br.com.crista.fashion.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {

    public OperacaoNaoPermitidaException (String msg) {
        super(msg);
    }

}
