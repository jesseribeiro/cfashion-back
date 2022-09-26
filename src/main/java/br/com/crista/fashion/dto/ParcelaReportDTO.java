package br.com.crista.fashion.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParcelaReportDTO {
    
    private String nuAutenticacaoLoja;
    private String vlParcela;
    private String dataVencimento;
    private Integer nuParcela;
    private String plano;
}
