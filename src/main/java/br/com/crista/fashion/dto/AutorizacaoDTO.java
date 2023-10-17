package br.com.crista.fashion.dto;

import java.math.BigDecimal;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutorizacaoDTO extends GenericDTO {

    private String dataInicial;
    private String dataFinal;

    private Long carneId;
    private Long vendaId;
    private Long lojaId;
    private Long redeId;
    private Long empresaId;
    private Long clienteId;
    private Long pagamentoId;
    private Calendar dataAutorizacao;
    private String cpf;
    private BigDecimal valorVenda;
    private BigDecimal valorTotal;
    private Integer qtdParcela;
    private String nomeLoja;
    private String codigoAutorizacao;
    private String tipoAutorizacao;
    private String nomeUsuario;
    private String recusado;
    private String situacao;
    private boolean podeCancelar;
    private String dataCompra;
}
