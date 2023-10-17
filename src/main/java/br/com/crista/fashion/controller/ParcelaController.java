package br.com.crista.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.service.ParcelaService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/parcela", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParcelaController {

    private final @NonNull
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
