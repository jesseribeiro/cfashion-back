package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.ClienteLojaDTO;
import br.com.crista.fashion.service.ClienteLojaService;
import br.com.crista.fashion.service.LojaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/clienteloja", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteLojaController extends GenericController {

    @Autowired
    private ClienteLojaService service;

    @Autowired
    private LojaService lojaService;

    @PostMapping
    public ResponseEntity insereClienteLoja(@RequestBody @Valid @NotNull ClienteLojaDTO dto) {
        return service.inserirClienteLoja(dto);
    }

    @PostMapping(path = "list-all-lojas-clientes/{id}")
    public ResponseEntity listAllLojasClientes(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.findAllLojasCliente(id));
        } catch(Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao atualizar: - " + e.getMessage());
        }
    }
}
