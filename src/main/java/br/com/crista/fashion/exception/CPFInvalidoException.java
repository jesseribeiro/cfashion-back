package br.com.crista.fashion.exception;

public class CPFInvalidoException extends RuntimeException {

    public CPFInvalidoException(){
        super("Número do CPF informado é inválido");
    }
}
