package br.com.crista.fashion.report;

import br.com.crista.fashion.dto.CarneReportDTO;

import java.util.Map;

public class CarneReport extends IReport {

    CarneReportDTO dto;

    public CarneReport(CarneReportDTO dto) {
        super(false);
        this.dto = dto;
    }
    @Override
    public Map<String, Object> getParametrosReport() {
        getParametros().put("parcelas", dto.getParcelas());
        getParametros().put("nomeLoja", dto.getNomeLoja());
        /*getParametros().put("peMultaMoratoria", dto.getPeMultaMoratoria());
        getParametros().put("nomeVendedor", dto.getNomeVendedor());
        getParametros().put("telefoneCliente", dto.getTelefoneCliente());
        getParametros().put("nuDiasAtraso", dto.getNuDiasAtraso());
        getParametros().put("cpf", dto.getCpf());
        getParametros().put("cidade", dto.getCidade());
        getParametros().put("endereco", dto.getEndereco());
        getParametros().put("telefoneLoja", dto.getTelefoneLoja());
        getParametros().put("nomeCliente", dto.getNomeCliente());
        getParametros().put("peCompensatoria", dto.getPeCompensatoria());
        getParametros().put("cnpj", dto.getCnpj());
        getParametros().put("nuContrato", dto.getNuContrato());
        getParametros().put("clienteId", dto.getClienteId());
        getParametros().put("vlTotal", dto.getVlTotal());
        getParametros().put("peComissaoPermanencia", dto.getPeComissaoPermanencia());*/
        return getParametros();
    }

    @Override
    public String getFileNameJrxml() {
        return "/carne_compra.jrxml";
    }
}
