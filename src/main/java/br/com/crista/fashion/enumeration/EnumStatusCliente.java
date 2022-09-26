package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumStatusCliente {
    ATIVO("Ativo"), // esse status não deve ser utilizado, ao invés utilizar o NORMAL
    NORMAL("NORMAL"),
    BLOQUEADO("BLOQUEADO"),
    BLOQUEADO_EM_ANALISE("BLOQUEADO - Em Análise"),
    BLOQUEADO_EM_ACORDO("BLOQUEADO - Em Acordo"),
    BLOQUEADO_EM_FERIAS("BLOQUEADO - Em Férias"),
    BLOQUEADO_CADASTRO_DIVERGENTE("BLOQUEADO - Cadastro Divergente"),
    BLOQUEADO_COBRANCA_AUTOMATICA("BLOQUEADO - Cobrança Automática"),
    BLOQUEADO_COBRANCA_JUDICIAL("BLOQUEADO - Cobrança Judicial"),
    BLOQUEADO_CREDITO_REJEITADO("BLOQUEADO - Crédito Rejeitado"),
    BLOQUEADO_INATIVO("BLOQUEADO - Inativo"),
    BLOQUEADO_SUSPEITA_FRAUDE("BLOQUEADO - Suspeito Fraude"),
    BLOQUEADO_OUTROS_MOTIVOS("BLOQUEADO - Outros Motivos"),
    CANCELADO_PEDIDO_ADMINISTRADORA("CANCELADO - Pedido Administradora"),
    CANCELADO_PEDIDO_CLIENTE("CANCELADO - Pedido Cliente"),
    CANCELADO_FALECIMENTO("CANCELADO - Falecimento"),
    CANCELADO_COBRANCA_JUDICIAL("CANCELADO - Cobrança Judicial"),
    CANCELADO_CREDITO_LIQUIDACAO("CANCELADO - Crédito Liquidação"),
    CANCELADO_MIGRACAO_DE_CONTA("CANCELADO - Migração de Conta"),
    CANCELADO_SUSPEITA_FRAUDE("CANCELADO - Suspeito Fraude"),
    CANCELADO_PEDIDO_CONVENIO("CANCELADO - Pedido Convênio"),
    CANCELADO_OUTROS_MOTIVOS("CANCELADO - Outros Motivos"),
    PENDENTE("Pendente"),
    RESTRICAO("Cliente com restrições"),
    ANALISE_VISUAL("Cliente em análise de crédito");

    private String label;

    EnumStatusCliente(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }

    public static EnumStatusCliente getValueOf(final String value) {
        if(value != null) {
            for(EnumStatusCliente e : EnumStatusCliente.values()) {
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
