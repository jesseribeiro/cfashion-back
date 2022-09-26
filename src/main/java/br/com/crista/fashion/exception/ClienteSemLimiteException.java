package br.com.crista.fashion.exception;

public class ClienteSemLimiteException extends RuntimeException {

    public ClienteSemLimiteException() {
        super("Cliente não possui limite para essa compra");
    }
}
