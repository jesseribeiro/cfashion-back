package br.com.crista.fashion.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.dto.CalcularVendaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.service.VendaService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/venda", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendaController {

    private final @NonNull
    VendaService vendaService;

    @PostMapping(path = "/pagination")
    public Page<VendaDTO> pagination(@RequestBody PaginationFilterDTO<VendaDTO> paginationFilter) {

        return vendaService.pagination(paginationFilter);
    }

    @PostMapping(path = "/vender")
    public ResponseEntity vender(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {

        try {

            return ResponseEntity.ok(vendaService.vender(dto));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/cancelar-venda/{id}")
    public ResponseEntity cancelarVenda(@PathVariable("id") Long vendaId) {

        try {

            vendaService.cancelarVenda(vendaId);

            return ResponseEntity.ok("Venda cancelada com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/pagar-venda/{id}")
    public ResponseEntity pagarVenda(@PathVariable("id") Long vendaId) {

        try {

            vendaService.pagarVenda(vendaId);

            return ResponseEntity.ok("Pagamento realizado com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/calcular-frete-desconto")
    public ResponseEntity calcularDesconto(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {

        try {

            return ResponseEntity.ok(vendaService.calcularFreteDesconto(dto));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/calcular-parcela")
    public ResponseEntity calcularParcela(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {

        try {

            return ResponseEntity.ok(vendaService.calcularParcela(dto));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/calcular-comissao")
    public ResponseEntity calcularComissao(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {

        try {

            return ResponseEntity.ok(vendaService.calcularComissao(dto));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
