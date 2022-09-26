package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumRole {
    ADMIN("Admin"),
    SUPERVISOR("Supervisor"),
    COMERCIAL("Comercial"),
    NEGOCIADOR("Negociador"),
    CREDIARISTA("Crediarista"),
    PROPRIETARIO("Proprietário"),
    PROPRIETARIO_RELATORIO("Proprietário Relatório");

    private String label;

    EnumRole(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }

    public String getLabel() {
        return label;
    }
}
