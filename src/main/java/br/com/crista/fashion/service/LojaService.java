package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.LojaEnderecoBean;
import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.VenderDTO;
import br.com.crista.fashion.enumeration.StatusLoja;
import br.com.crista.fashion.repository.LojaEnderecoRepository;
import br.com.crista.fashion.repository.LojaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LojaService extends GenericService<LojaBean, LojaRepository> {

    @Autowired
    private LojaEnderecoRepository lojaEnderecoRepository;

    @Autowired
    private CentralConfig centralConfig;

    @Autowired
    private CidadeService cidadeService;

    public List<LojaBean> findAll(){
        return convertIterableToList(getRepository().findAll());
    }

    public LojaDTO salvar(LojaDTO lojaDTO) {

        validaEndereco(lojaDTO);

        LojaBean loja = new LojaBean();
        loja = lojaDTO.converter(loja);

        String cidadeNome = cidadeService.getCidadeNomeByCidadeIbge(lojaDTO.getEndereco().getCidadeIbge());
        lojaDTO.getEndereco().setCidadeNome(cidadeNome);
        LojaEnderecoBean lojaEndereco = new LojaEnderecoBean(lojaDTO.getEndereco());
        lojaEnderecoRepository.save(lojaEndereco);
        loja.setEndereco(lojaEndereco);
        if(lojaDTO.getRamoAtividadeId() == null){
            throw new RuntimeException("Informe um ramo de atividade");
        }

        save(loja);

        LojaDTO dto = new LojaDTO(loja);
        return dto;
    }

    private void validaEndereco(LojaDTO lojaDTO) {
        if(lojaDTO.getEndereco() == null ||
                lojaDTO.getEndereco().getCep() == null ||
                lojaDTO.getEndereco().getCep().isEmpty() ||
                lojaDTO.getEndereco().getCidadeIbge() == null) {
            throw new RuntimeException("O campo endereço é obrigatório, adicione um endereço e tente novamente.");
        }
    }

    public Page<LojaDTO> pagination(PaginationFilterDTO<LojaDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        LojaDTO filtros = paginationFilter.getFiltros();

        //TODO pegar o ID da Rede ou Empresa do Usuário logado

        StatusLoja statusLoja = filtros.getStatus() != null ? StatusLoja.valueOf(filtros.getStatus()) : null;
        Page<LojaDTO> lojas = getRepository().pagination(filtros.getId(), filtros.getRazaoSocial(), filtros.getNomeFantasia()
                ,filtros.getCnpj(), statusLoja, paging);
        if(lojas.hasContent()) {
            return lojas;
        } else {
            return Page.empty();
        }
    }

    public LojaDTO getByIdDTO(Long id) {
       LojaBean loja = getLojaById(id);

       LojaDTO dto = new LojaDTO(loja);
       return dto;
    }

    public LojaDTO update(Long id, LojaDTO dto) {

        validaEndereco(dto);
        LojaBean loja = getLojaById(id);
        loja.setRazaoSocial(dto.getRazaoSocial());
        loja.setNomeFantasia(dto.getNomeFantasia());
        //loja.setCnpj(dto.getCnpj());
        loja.setStatus(StatusLoja.valueOf(dto.getStatus()));
        loja.setTelComercial(dto.getTelComercial());
        loja.setPessoaContato(dto.getPessoaContato());
        loja.setSite(dto.getSite());
        loja.setObservacoes(dto.getObservacoes());
        loja.setIsEmitirCartaoLoja(dto.isEmitirCartaoLoja());
        loja.setIsEnviarSmsBoasVindas(dto.isEnvioSMSBoasVindas());
        loja.setIsPermitePagtoLoja(dto.isPermitirPagamentoLoja());
        loja.setIsPermiteAutorizacaoCompraApp(dto.isPermitirAutorizacaoCompra());
        loja.setIsPossuiLimiteExclusivo(dto.isPossuiLimiteExclusivo());
        loja.setCampanhaAtiva(dto.isCampanhaAtiva());
        loja.setCrediarioProprio(dto.isCrediarioProprio());
        loja.setCampanhaQtdVendas(dto.getCampanhaQtdVendas());
        loja.setCampanhaQtdDiasPrimeiraParcela(dto.getCampanhaQtdDiasPrimeiraParcela());
        loja.setEmail(dto.getEmail());
        loja.setWhatsapp(dto.getWhatsapp());

        String cidadeNome = cidadeService.getCidadeNomeByCidadeIbge(dto.getEndereco().getCidadeIbge());
        dto.getEndereco().setCidadeNome(cidadeNome);
        if(loja.getEndereco() == null) {
            LojaEnderecoBean lojaEndereco = new LojaEnderecoBean(dto.getEndereco());
            lojaEnderecoRepository.save(lojaEndereco);
            loja.setEndereco(lojaEndereco);
        } else {
            loja.getEndereco().setEndereco(dto.getEndereco());
            lojaEnderecoRepository.save(loja.getEndereco());
        }

        update(loja);

        LojaDTO lojaDTO = new LojaDTO(loja);
        return lojaDTO;
    }

    public LojaBean getLojaById(Long id) {
        return getRepository().findById(id).orElse(null);
    }

    public void delete(Long id) {
        LojaBean loja = getLojaById(id);
        delete(loja);
    }

    public VenderDTO getDadosVenda(Long lojaId, Long planoPagamentoId) {

        VenderDTO venderDTO = new VenderDTO();

        venderDTO.setFormasPagamento(getListaFormasPagamento(lojaId));
        LojaBean loja = getLojaById(lojaId);
        venderDTO.setLojistaEmail(loja.getEmail());
        return venderDTO;
    }

    public List<String> getListaFormasPagamento(Long lojaId) {
        List<String> listaFormasPagamento = new ArrayList();
        return listaFormasPagamento;
    }

    public LojaBean findLojaByRazaoSocial (String nomeLoja) {
        return getRepository().findLojaByRazaoSocial(nomeLoja);
    }

    public Long qtdVendasLojaMes(Long seCampAtiva) {
        return getRepository().qtdVendasLojaMes(seCampAtiva);
    }
}
