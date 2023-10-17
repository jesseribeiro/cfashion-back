package br.com.crista.fashion.dto;

import lombok.Data;

@Data
public class FilterAuditDTO extends GenericDTO {

    private String entity;
    private String usuario;
    private String cpfCliente;
    private String dataInicial;
    private String dataFinal;
    private boolean exibirDetalhes;
    private Long operacao;
    private Long clienteId;
    private Long lojaId;

    public Class getClassOfEntity() throws ClassNotFoundException {

        return Class.forName("br.com.crista.fashion.bean."+entity);
    }
}
