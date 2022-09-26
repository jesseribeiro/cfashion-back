package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.CarneClienteDTO;
import br.com.crista.fashion.enumeration.EnumStatusCarne;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository("carneRepositoryImpl")
public class CarneRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;


    public List<CarneClienteDTO> findCarnesCliente(Long clienteId, Long lojaId, Calendar dataInicial, Calendar dataFinal,
                                                   EnumStatusCarne status, Boolean somenteCarne) {
        List<CarneClienteDTO> carnes = new ArrayList<>();
        String sqlCarnesCompra = " SELECT new br.com.crista.fashion.dto.CarneClienteDTO(cc) From Carne as cc " +
                " JOIN cc.venda as v " +
                " JOIN v.loja as l " +
                " JOIN v.cliente as c " +
                " WHERE " +
                " c.id = :clienteId " +
                " and l.id = :lojaId ";
        String filtros = "";
        if(dataInicial != null) {
            filtros += " and cc.dataCompra >= :dataInicial ";
        }
        if(dataFinal != null) {
            filtros += " and cc.dataCompra <= :dataFinal ";
        }
        if(status != null) {
            filtros += " and cc.status = :status ";
        }
        if(somenteCarne) {
            filtros += " and cc.formaPagamento = 'CARNE' ";
        }
        sqlCarnesCompra += filtros;

        Query query = entityManager.createQuery(sqlCarnesCompra);
        query.setParameter("clienteId", clienteId);
        query.setParameter("lojaId", lojaId);
        addFiltros(dataInicial, dataFinal, status, query);
        List<CarneClienteDTO> listaCarneCompras = query.getResultList();

        carnes.addAll(listaCarneCompras);

        String sqlCarnesRenegociado = " SELECT new br.com.crista.fashion.dto.CarneClienteDTO(cc) From Carne as cc " +
                " JOIN cc.renegociacao as r ";
                if(lojaId != null){
                    sqlCarnesRenegociado += " JOIN r.loja as l on l.id = :lojaId ";
                }
                sqlCarnesRenegociado +=" JOIN r.cliente as c " +
                " WHERE " +
                " c.id = :clienteId ";

        sqlCarnesRenegociado += filtros;

        query = entityManager.createQuery(sqlCarnesRenegociado);
        query.setParameter("clienteId", clienteId);
        if (lojaId != null){
           query.setParameter("lojaId", lojaId);
        }
        addFiltros(dataInicial, dataFinal, status, query);
        List<CarneClienteDTO> listaCarneRenegociados = query.getResultList();

        carnes.addAll(listaCarneRenegociados);

        return carnes;
    }

    private void addFiltros(Calendar dataInicial, Calendar dataFinal, EnumStatusCarne status, Query query) {
        if (status != null) {
            query.setParameter("status", status);
        }
        if (dataInicial != null) {
            query.setParameter("dataInicial", DateUtils.zeraHorario(dataInicial));
        }
        if (dataFinal != null) {
            query.setParameter("dataFinal", DateUtils.setUltimaHoraDoDia(dataFinal));
        }
    }
}
