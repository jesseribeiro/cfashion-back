package br.com.crista.fashion.exception;

public class PlanoPagamentoNaoPodeSerExcluidoException extends RuntimeException {

    public PlanoPagamentoNaoPodeSerExcluidoException() {
        super("Este plano de pagamento esta em uso na Regra Financeira, portanto não pode ser excluído. Operação cancelada.");
    }
}
