package br.com.crista.fashion.enumeration;

public enum TipoRegra {
    REGRA_1,
    REGRA_2;

    public int getValue() {
        return ordinal() + 1;
    }

}
