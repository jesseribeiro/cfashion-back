package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumOperacaoAuditoria {
    //modificação, criação e remoção)
    CRIACAO(0,"Criação"),
    MODIFICACAO(1,"Modificação"),
    EXCLUSAO(2,"Exclusão");

    private int value;
    private String descricao;

    EnumOperacaoAuditoria(int value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public static List<LabelDescricaoDTO> getDescricoes(){
        return Arrays.stream(values()).map(operacaoAuditoria -> new LabelDescricaoDTO(String.valueOf(operacaoAuditoria.getValue()), operacaoAuditoria.getDescricao())).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

}
