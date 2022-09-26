package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumRecusaVenda {
    CARTAO_BLOQUEADO("Cartão bloqueado"),
    CLIENTE_ATRASO("Cliente com atraso"),
    CLIENTE_RESTRICAO("Cliente com restrição"),
    FALTA_LIMITE("Falta de limite"),
    ERRO_SPC("Erro de consulta SPC"),
    FALTA_PLANO("Falta plano de pagamento");

    private String label;

    EnumRecusaVenda(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }
}
