package br.com.crista.fashion.exception;

public class CodigoAutorizacaoInvalidoOrExpiradoException extends RuntimeException {
    public CodigoAutorizacaoInvalidoOrExpiradoException() {
        super("Código de autorização está inválido ou expirado, deve ser gerado um novo");
    }
}
