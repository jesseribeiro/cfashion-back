package br.com.crista.fashion.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.LojaService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/loja", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LojaController {

    private final @NonNull
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
