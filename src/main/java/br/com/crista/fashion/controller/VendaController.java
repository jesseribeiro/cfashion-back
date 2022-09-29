package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/venda", produces = MediaType.APPLICATION_JSON_VALUE)
public class VendaController {

    @Autowired
    VendaService vendaService;

    @PostMapping(path = "/pagination")
    public Page<VendaDTO> pagination(@RequestBody PaginationFilterDTO<VendaDTO> paginationFilter) {
        return vendaService.pagination(paginationFilter);
    }

    /*
    @PostMapping(path = "/calcular")
    public ResponseEntity calcularVenda(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {
        try {
            return ResponseEntity.ok(vendaService.calcularVenda(dto));
        } catch (OperacaoNaoPermitidaException e) {
            return ResponseEntity.status(METHOD_NOT_ALLOWED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    */

    @PostMapping(path = "/vender")
    public ResponseEntity vender(@RequestBody @Valid @NotNull CalcularVendaDTO dto) throws IOException {
        try {
            return ResponseEntity.ok(vendaService.vender(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/cancelar-venda/{id}", method = RequestMethod.GET)
    public ResponseEntity cancelarVenda(@PathVariable("id") Long vendaId) {
        try {
            vendaService.cancelarVenda(vendaId);
            return ResponseEntity.ok("Venda cancelada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
