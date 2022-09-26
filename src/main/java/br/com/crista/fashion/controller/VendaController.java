package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.exception.OperacaoNaoPermitidaException;
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
import java.util.List;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/venda", produces = MediaType.APPLICATION_JSON_VALUE)
public class VendaController {

    @Autowired
    private VendaService vendaService;

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

    @ResponseBody
    @RequestMapping(value = "/recibo-pagamento/{id}", method = RequestMethod.GET)
    public ReciboPagamentoDTO reciboPagamento(@PathVariable("id") Long pagamentoId) {
        return vendaService.getReciboPagamento(pagamentoId);
    }

    @ResponseBody
    @RequestMapping(value = "/recibo-venda/{id}", method = RequestMethod.GET)
    public ReciboVendaDTO reciboVenda(@PathVariable("id") Long vendaId) {
        return vendaService.getReciboVenda(vendaId);
    }

    @PostMapping(path = "/pagar-carne")
    public ResponseEntity pagarCarne(@RequestBody @Valid @NotNull ParcelaClienteDTO dto) {
        try {
            vendaService.pagarCarne(dto);
            return ResponseEntity.ok("Pagamento realizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/pagar-lista-carne")
    public ResponseEntity pagarListaCarne(@RequestBody @Valid @NotNull List<ParcelaClienteDTO> parcelas) {
        try {
            vendaService.pagarListaCarne(parcelas);
            return ResponseEntity.ok("Pagamento realizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/cancelar-pagamento")
    public ResponseEntity cancelarPagamento(@RequestBody @Valid @NotNull CancelarPagamentoDTO dto) {
        try {
            vendaService.cancelarPagamento(dto);
            return ResponseEntity.ok("Pagamento cancelado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/autorizacoes")
    public ResponseEntity paginationAutorizacoes(@RequestBody PaginationFilterDTO<AutorizacaoDTO> paginationFilter) {
        try {
            Page<AutorizacaoDTO> result = vendaService.paginationAutorizacoes(paginationFilter);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/venda-na-mao")
    public ResponseEntity vendaNaMao(@RequestBody VendaNaMaoDTO dto) {
        try {
            return ResponseEntity.ok(vendaService.vendaNaMao(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
