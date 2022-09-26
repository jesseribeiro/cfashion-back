package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumTipoDocumento {
    P("Proposta Adesão"),
    F("RG (frente)"),
    V("RGH (verso)"),
    C("CNH"),
    O("Outros");

    private String label;

    EnumTipoDocumento(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }
}
