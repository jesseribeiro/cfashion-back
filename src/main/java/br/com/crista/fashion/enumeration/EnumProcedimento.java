package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumProcedimento {
    CONTATO_TELEFONICO("Contato Telefônico"),
    AGENDAMENTO_PAGAMENTO("Agendamento Pagamento"),
    AGENDAMENTO_ACORDO("Agendamento Acordo"),
    ACORDO("Acordo"),
    OUTROS("Outros");

    private String label;

    EnumProcedimento(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }

    public static EnumProcedimento getValue(String value) {
        for (EnumProcedimento e : values()) {
            if(e.name().equals(value)) {
                return e;
            }
            if(String.valueOf(e.ordinal()) == value) {
                return e;
            }
            if(e.getLabel().equals(value)) {
                return e;
            }
        }
        return OUTROS;
    }
}
