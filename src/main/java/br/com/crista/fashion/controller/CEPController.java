package br.com.crista.fashion.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.istack.NotNull;

import br.com.crista.fashion.bean.CepBean;
import br.com.crista.fashion.service.CEPService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/cep")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CEPController {

    private final @NonNull
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
