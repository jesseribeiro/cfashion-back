package br.com.crista.fashion.exception;

public class UsuarioInativoException extends RuntimeException {

    public UsuarioInativoException(){
        super("Usuário inativo");
    }
}
