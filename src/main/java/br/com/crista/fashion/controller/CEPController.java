package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.CepBean;
import br.com.crista.fashion.service.CEPService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@Slf4j
@RestController
@RequestMapping("/v1/cep")
public class CEPController {

    @Autowired
    CEPService cepService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull CepBean cep) {
        return cepService.salvar(cep);
    }

    @GetMapping("/{cep}")
    public CepBean buscaCEP(@PathVariable("cep") String cep) {
        return cepService.findCep(cep);
    }
}
