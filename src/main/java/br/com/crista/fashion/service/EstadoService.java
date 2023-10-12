package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.EstadoBean;
import br.com.crista.fashion.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EstadoService extends GenericService<EstadoBean, EstadoRepository>{

    public List<EstadoBean> findAll() {

        return convertIterableToList(getRepository().findAll());
    }

    public EstadoBean findById(Long estadoId) {

        return getRepository().findById(estadoId).orElseThrow(()-> new EntityNotFoundException("Estado não encontrado"));
    }
}
