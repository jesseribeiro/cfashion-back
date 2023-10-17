package br.com.crista.fashion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.bean.EstadoBean;
import br.com.crista.fashion.service.EstadoService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/estado", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstadoController {

    private final @NonNull
    EstadoService estadoService;

    @GetMapping
    public List<EstadoBean> findAll(){
        return estadoService.findAll();
    }

    @GetMapping(path = "/{id}")
    public EstadoBean findById(@PathVariable("id") Long id){
        return estadoService.findById(id);
    }
}
