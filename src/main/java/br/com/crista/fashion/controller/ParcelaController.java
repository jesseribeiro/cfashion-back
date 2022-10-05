package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.service.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/parcela", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParcelaController {

    @Autowired
    ParcelaService parcelaService;

    @PostMapping(path = "/pagination")
    public Page<ParcelaDTO> pagination(@RequestBody PaginationFilterDTO<ParcelaDTO> paginationFilter) {
        return parcelaService.pagination(paginationFilter);
    }


    @PostMapping(path = "/cancelar-parcela/{id}")
    public ResponseEntity cancelarParcela(@PathVariable("id") Long parcelaId) {
        try {
            parcelaService.cancelarParcela(parcelaId);
            return ResponseEntity.ok("Parcela cancelada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/pagar-parcela/{id}")
    public ResponseEntity pagarParcela(@PathVariable("id") Long parcelaId) {
        try {
            parcelaService.pagarParcela(parcelaId);
            return ResponseEntity.ok("Pagamento realizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
