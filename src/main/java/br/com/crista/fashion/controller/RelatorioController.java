package br.com.crista.fashion.controller;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.RespostaErroDTO;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesPDF;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesXLS;
import br.com.crista.fashion.repository.impl.ComprasClientesRepositoryImpl;
import br.com.crista.fashion.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelatorioController {

    @Autowired
    CentralConfig centralConfig;

    @Autowired
    ComprasClientesRepositoryImpl comprasClientes;

    @ResponseBody
    @RequestMapping(value = "/compras-clientes", method = RequestMethod.POST)
    public ResponseEntity getComprasClientes(@RequestBody FiltroRelatorioDTO filtro) {
        try {
            List<ComprasDTO> dados = comprasClientes.getComprasClientes(filtro);
            if (dados != null && !dados.isEmpty()) {
                switch (filtro.getTipoRel()) {
                    case XLS:
                        ComprasClientesXLS reportXLS = new ComprasClientesXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);
                    default: // PDF
                        ComprasClientesPDF reportPDF = new ComprasClientesPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Nenhum registro foi encontrado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Erro encontrado: " + e.getMessage()));
        }
    }
}