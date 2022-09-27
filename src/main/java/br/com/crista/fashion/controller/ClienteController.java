package br.com.crista.fashion.controller;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.service.ClienteService;
import br.com.crista.fashion.service.LojaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClienteService clienteService;

    @Autowired
    private CentralConfig centralConfig;

    @Autowired
    private LojaService lojaService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
    @PostMapping(path = "/pagination")
    public Page<ClienteLojaDTO> pagination(@RequestBody PaginationFilterDTO<ClienteDTO> paginationFilter) {
        return clienteService.pagination(paginationFilter);
    }
    */

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

    /*
    @PostMapping(path = "/{cpf}/parcelas")
    public Page<ParcelaClienteDTO> paginationCarnes(@PathVariable("cpf") String cpf, @RequestBody PaginationFilterDTO<ParcelaClienteDTO> paginationFilter) {
        return clienteService.paginationParcelas(cpf, paginationFilter);
    }
    */
}
