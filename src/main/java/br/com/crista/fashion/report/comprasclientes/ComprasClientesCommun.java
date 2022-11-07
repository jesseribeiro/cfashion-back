package br.com.crista.fashion.report.comprasclientes;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.DateUtils;

public class ComprasClientesCommun {

    public static String cabecalho (FiltroRelatorioDTO filtro) {
        String texto = "Compras por Clientes";
        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            texto += " de " + DateUtils.getDiaMesAnoPortugues(filtro.getDataInicio()) + " a " + DateUtils.getDiaMesAnoPortugues(filtro.getDataFim());
        } else if (filtro.getDataInicio() != null) {
            texto += " a partir de " + DateUtils.getDiaMesAnoPortugues(filtro.getDataInicio());
        } else if (filtro.getDataFim() != null) {
            texto += " até " + DateUtils.getDiaMesAnoPortugues(filtro.getDataFim());
        }
        return texto;
    }
}