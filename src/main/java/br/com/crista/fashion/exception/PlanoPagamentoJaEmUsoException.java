package br.com.crista.fashion.exception;

public class PlanoPagamentoJaEmUsoException extends RuntimeException {

    public PlanoPagamentoJaEmUsoException() {
        super("Este plano de pagamento já foi utilizado em uma compra. Operação cancelada.");
    }
}
