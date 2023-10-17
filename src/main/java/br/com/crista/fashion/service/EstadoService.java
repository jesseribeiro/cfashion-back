package br.com.crista.fashion.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.EstadoBean;
import br.com.crista.fashion.repository.EstadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstadoService extends GenericService<EstadoBean, EstadoRepository>{

    public List<EstadoBean> findAll() {

        return convertIterableToList(getRepository().findAll());
    }

    public EstadoBean findById(Long estadoId) {

        return getRepository().findById(estadoId)
                .orElseThrow(()-> new EntityNotFoundException("Estado não encontrado"));
    }
}
