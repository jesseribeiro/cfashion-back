package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumSaldoDevedor {
    TODAS(0, "Todas as faixas", new BigDecimal(0)),
    ATE50(1, "Até R$ 50,00", new BigDecimal(50)),
    ATE100(2, "Até R$ 100,00", new BigDecimal(100)),
    ATE500(3, "Até R$ 500,00", new BigDecimal(500)),
    ATE1000(4, "Até R$ 1000,00", new BigDecimal(1000)),
    ATE1500(5, "Até R$ 1500,00", new BigDecimal(1500)),
    ATE2000(6, "Até R$ 2000,00", new BigDecimal(2000)),
    ATE2500(7, "Até R$ 2500,00", new BigDecimal(2500)),
    ATE3000(8, "Até R$ 3000,00", new BigDecimal(3000)),
    ATE3500(9, "Até R$ 3500,00", new BigDecimal(3500)),
    ATE4000(10, "Até R$ 4000,00", new BigDecimal(4000)),
    ATE4500(11, "Até R$ 4500,00", new BigDecimal(4500)),
    ATE5000(12, "Até R$ 5000,00", new BigDecimal(5000)),
    ATE6000(13, "Até R$ 6000,00", new BigDecimal(6000)),
    ATE7000(14, "Até R$ 7000,00", new BigDecimal(7000)),
    ATE8000(15, "Até R$ 8000,00", new BigDecimal(8000)),
    ATE9000(16, "Até R$ 9000,00", new BigDecimal(9000)),
    ATE10000(17, "Até R$ 10000,00", new BigDecimal(10000)),
    ACIMA50(18, "Acima R$ 50,00", new BigDecimal(50)),
    ACIMA100(19, "Acima R$ 100,00", new BigDecimal(100)),
    ACIMA500(20, "Acima R$ 500,00", new BigDecimal(500)),
    ACIMA1000(21, "Acima R$ 1000,00", new BigDecimal(1000)),
    ACIMA1500(22, "Acima R$ 1500,00", new BigDecimal(1500)),
    ACIMA2000(23, "Acima R$ 2000,00", new BigDecimal(2000)),
    ACIMA2500(24, "Acima R$ 2500,00", new BigDecimal(2500)),
    ACIMA3000(25, "Acima R$ 3000,00", new BigDecimal(3000)),
    ACIMA3500(26, "Acima R$ 3500,00", new BigDecimal(3500)),
    ACIMA4000(27, "Acima R$ 4000,00", new BigDecimal(4000)),
    ACIMA4500(28, "Acima R$ 4500,00", new BigDecimal(4500)),
    ACIMA5000(29, "Acima R$ 5000,00", new BigDecimal(5000)),
    ACIMA6000(30, "Acima R$ 6000,00", new BigDecimal(6000)),
    ACIMA7000(31, "Acima R$ 7000,00", new BigDecimal(7000)),
    ACIMA8000(32, "Acima R$ 8000,00", new BigDecimal(8000)),
    ACIMA9000(33, "Acima R$ 9000,00", new BigDecimal(9000)),
    ACIMA10000(34, "Acima R$ 10000,00", new BigDecimal(10000));

    private int value;
    private String label;
    private BigDecimal valorReais;

    EnumSaldoDevedor(int value, String label, BigDecimal valorReais){
        this.value = value;
        this.label = label;
        this.valorReais = valorReais;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public BigDecimal getValorReais() {
        return valorReais;
    }
}
