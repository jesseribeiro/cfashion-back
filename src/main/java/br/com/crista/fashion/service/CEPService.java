package br.com.crista.fashion.service;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.CepBean;
import br.com.crista.fashion.exception.CepNaoEcontradoException;
import br.com.crista.fashion.feign.ViaCEPClient;
import br.com.crista.fashion.repository.CepRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CEPService extends GenericService<CepBean, CepRepository> {

    private final @NonNull
    ViaCEPClient viaCEPClient;

    public ResponseEntity salvar(CepBean cep) {

        CepBean cepBean = getRepository().findByCep(cep.getCep());

        if (nonNull(cepBean)) {

            cep.setCep(cep.getCep().replaceAll("\\D+", ""));
            getRepository().save(cep);
        }

        return ResponseEntity.ok().body("CEP cadastrado com sucesso");
    }

    public CepBean findCep(String cep) {

        CepBean cepBean = getRepository().findByCep(cep);

        if (nonNull(cepBean)) {

            return cepBean;
        }

        cepBean = viaCEPClient.buscaEnderecoPor(cep);

        if (nonNull(cepBean) && nonNull(cepBean.getCep())) {

            salvar(cepBean);

            return cepBean;
        }

        throw new CepNaoEcontradoException();
    }
}
