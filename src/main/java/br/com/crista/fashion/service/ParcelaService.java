package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.repository.ParcelaRepository;
import br.com.crista.fashion.repository.impl.ParcelaRepositoryImpl;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ParcelaService extends GenericService<ParcelaBean, ParcelaRepository> {

    @Autowired
    ParcelaRepositoryImpl parcelaRepository;

    @Autowired
    LojaService lojaService;

    @Autowired
    ClienteService clienteService;

    public Page<ParcelaDTO> pagination(PaginationFilterDTO<ParcelaDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ParcelaDTO filtros = paginationFilter.getFiltros();

        Long clienteId = null;
        Long marcaId = null;
        EnumStatus status = filtros.getStatus() != null ? EnumStatus.valueOf(filtros.getStatus()) : null;

        if (filtros.getCpf() != null && !filtros.getCpf().isEmpty()) {
            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }

        if (filtros.getMarcaId() != null) {
            LojaBean marca = lojaService.getById(filtros.getMarcaId());
            marcaId = marca.getId();
        }

        Page<ParcelaDTO> parcelas = parcelaRepository.pagination(
                clienteId,
                marcaId,
                status,
                filtros.getDataInicial(),
                filtros.getDataFinal(),
                paging);

        if (parcelas.hasContent()) {
            return parcelas;
        } else {
            return Page.empty();
        }
    }

    @Transactional
    public void cancelarParcela(Long parcelaId) {
        ParcelaBean parcela = getRepository().findById(parcelaId).get();
        parcela.setStatus(EnumStatus.CANCELADA);
        update(parcela);
    }

    @Transactional
    public void pagarParcela(Long parcelaId) {
        ParcelaBean parcela = getRepository().findById(parcelaId).get();
        parcela.setStatus(EnumStatus.PAGA);
        update(parcela);
    }

    public void updateParcelasPagas(List<ParcelaBean> parcelas) {
        for (ParcelaBean bean : parcelas) {
            if (bean.getStatus() != EnumStatus.PAGA) {
                bean.setDataPagto(Calendar.getInstance());
                bean.setStatus(EnumStatus.PAGA);
                update(bean);
            }
        }
    }

    public void updateParcelasCanceladas(List<ParcelaBean> parcelas) {
        for (ParcelaBean bean : parcelas) {
            bean.setStatus(EnumStatus.CANCELADA);
            update(bean);
        }
    }
}
