package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumSituacaoConta {
    TODAS("Todas"),
    BLQ_COBRANCA_AUTOMATICA("Bloqueado - Cobrança Automática"),
    BlQ_COBRANCA_JUDICIAL("Bloqueado - Cobrança Judicial"),
    BLQ_CREDITO_REJEITADO("Bloqueado - Crédito Rejeitado"),
    BLQ_EM_ACORDO("Bloqueado - Em Acordo"),
    BLQ_EM_FERIAS("Bloqueado - Em Férias"),
    BLQ_INATIVO("Bloqueado - Inativo"),
    BLQ_OUTROS_MOTIVOS("Bloqueado - Outros Motivos"),
    BLQ_SUSPEITA_FRAUDE("Bloqueado - Suspeita Fraude"),
    CANCEL_COBRANCA_JUDICIAL("Cancelado - Cobrança Judicial"),
    CANCEL_CREDITO_LIQUIDACAO("Cancelado - Crédito Liquidação"),
    CANCEL_FALECIMENTO("Cancelado - Falecimento"),
    CANCEL_MIGRACAO_CONTA("Cancelado - Migração de Conta"),
    CANCEL_OUTROS_MOTIVOS("Cancelado - Outros Motivos"),
    CANCEL_PEDIDO_ADMINISTRADORA("Cancelado - Pedido Administradora"),
    CANCEL_PEDIDO_CLIENTE("Cancelado - Pedido Cliente"),
    CANCEL_PEDIDO_CONVENIO("Cancelado - Pedido Convênio"),
    CANCEL_SUSPEITA_FRAUDE("Cancelado - Suspeita de Fraude"),
    NORMAL("Normal");

    private String label;

    EnumSituacaoConta(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }

    public static EnumSituacaoConta getValueOf(final String value) {
        if(value != null) {
            for(EnumSituacaoConta e : EnumSituacaoConta.values()) {
                if(e.getLabel().equals(value)) {
                    return e;
                }
                if(e.name().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }
}
