package br.com.crista.fashion.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.service.ClienteService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController extends GenericController {

    private final @NonNull
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
        } catch(Exception e) {

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
