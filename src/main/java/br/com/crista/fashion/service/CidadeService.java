package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.CidadeBean;
import br.com.crista.fashion.utils.StringUtils;
import br.com.crista.fashion.repository.CidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class CidadeService extends GenericService<CidadeBean, CidadeRepository> {

    @Autowired
    CidadeRepository cidadeRepository;

    public List<CidadeBean> findAll() {
        return convertIterableToList(getRepository().findAll());
    }

    public CidadeBean findById(Long id) {
        return cidadeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cidade não encontrada"));
    }

    public List<CidadeBean> findByUf(String uf) {
        return cidadeRepository.findByUf(uf);
    }

    public String getCidadeNomeByCidadeIbge(Integer cidadeIbje) {
        if(cidadeIbje != null) {
            CidadeBean cidade = cidadeRepository.findByIbge(cidadeIbje);
            return cidade.getNome();
        }
        return null;
    }

    public void updateCidadeSemAcento () {
        List<CidadeBean> cidades = findAll();
        for (CidadeBean bean : cidades) {
            bean.setNomeSemAcento(StringUtils.removeAcentos(bean.getNome()));
            update(bean);
        }
        log.info("Finalização!!");
    }

}
