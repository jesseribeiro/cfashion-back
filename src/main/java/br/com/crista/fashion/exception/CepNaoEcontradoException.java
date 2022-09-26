package br.com.crista.fashion.exception;

public class CepNaoEcontradoException extends RuntimeException {

    public CepNaoEcontradoException(){
        super("CEP não encontrado!");
    }
}
