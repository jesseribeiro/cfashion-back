package br.com.crista.fashion.service;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.CidadeBean;
import br.com.crista.fashion.repository.CidadeRepository;
import br.com.crista.fashion.utils.StringUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CidadeService extends GenericService<CidadeBean, CidadeRepository> {

    private final @NonNull
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

    public String getCidadeNomeByCidadeIbge(Integer cidadeIbge) {

        if (nonNull(cidadeIbge)) {

            CidadeBean cidade = cidadeRepository.findByIbge(cidadeIbge);

            return cidade.getNome();
        }

        return null;
    }

    public void updateCidadeSemAcento () {

        List<CidadeBean> cidades = findAll();

        cidades.forEach(cidadeBean -> {

            cidadeBean.setNomeSemAcento(StringUtils.removeAcentos(cidadeBean.getNome()));
            update(cidadeBean);
        });
    }
}
