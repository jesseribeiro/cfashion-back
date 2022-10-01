package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController extends GenericController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/pagination")
    public Page<ClienteDTO> pagination(@RequestBody PaginationFilterDTO<ClienteDTO> paginationFilter) {
        return clienteService.pagination(paginationFilter);
    }

    @GetMapping
    public List<ClienteDTO> findAll() {
        return clienteService.findAllDTO();
    }

    @GetMapping(path = "/{id}")
    public ClienteDTO getById(@PathVariable("id") Long id) {
        return clienteService.getClienteDTOById(id);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.update(id, dto));
        } catch(Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao atualizar: - " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return clienteService.delete(id);
    }


    @PostMapping(path = "/{id}/vendas")
    public List<VendaDTO> getVendasCliente(@PathVariable("id") Long id, @RequestBody VendaDTO filtro) {
        return clienteService.getVendasCliente(id, filtro);
    }

    @PostMapping(path = "/{id}/pagamentos")
    public Page<ParcelaDTO> paginationPagamentos(@PathVariable("id") Long id, @RequestBody PaginationFilterDTO<ParcelaDTO> paginationFilter) {
        return clienteService.paginationPagamentos(id, paginationFilter);
    }

    @PostMapping(path = "/consulta-cpf")
    public ResponseEntity consultaCPF(@RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.consultaCPF(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
