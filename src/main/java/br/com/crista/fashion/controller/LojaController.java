package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.LojaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/loja", produces = MediaType.APPLICATION_JSON_VALUE)
public class LojaController {

    @Autowired
    LojaService lojaService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull LojaDTO lojaDTO) {

        try {

            return ResponseEntity.ok(lojaService.salvar(lojaDTO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAll() {

        List<LojaBean> lojas = lojaService.findAll();

        return new ResponseEntity<>(lojas, HttpStatus.OK);
    }

    @PostMapping(path = "/pagination")
    public Page<LojaDTO> pagination(@RequestBody PaginationFilterDTO<LojaDTO> paginationFilter) {

        return lojaService.pagination(paginationFilter);
    }

    @GetMapping(path = "/{id}")
    public LojaDTO getById(@PathVariable("id") Long id) {

        return lojaService.getByIdDTO(id);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull LojaDTO dto) {

        try {

            return ResponseEntity.ok(lojaService.update(id, dto));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        try {

            lojaService.delete(id);

            return ResponseEntity.ok("Loja excluída com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
