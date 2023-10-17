package br.com.crista.fashion.service;

import static java.util.Objects.nonNull;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.repository.ParcelaRepository;
import br.com.crista.fashion.repository.impl.ParcelaRepositoryImpl;
import br.com.crista.fashion.utils.StringUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParcelaService extends GenericService<ParcelaBean, ParcelaRepository> {

    private final @NonNull
    ParcelaRepositoryImpl parcelaRepository;

    private final @NonNull
    LojaService lojaService;

    private final @NonNull
    ClienteService clienteService;

    public Page<ParcelaDTO> pagination(PaginationFilterDTO<ParcelaDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ParcelaDTO filtros = paginationFilter.getFiltros();

        Long clienteId = null;
        Long marcaId = null;

        EnumStatus status = nonNull(filtros.getStatus()) ? EnumStatus.valueOf(filtros.getStatus()) : null;

        if (nonNull(filtros.getCpf()) && !filtros.getCpf().isEmpty()) {

            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }

        if (nonNull(filtros.getMarcaId())) {

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
