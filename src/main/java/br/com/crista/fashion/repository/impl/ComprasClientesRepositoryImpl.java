package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository("comprasClientesRepositoryImpl")
public class ComprasClientesRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List <ComprasDTO> getComprasClientes(FiltroRelatorioDTO filtro) {
        String sqlBase = " select to_char(v.data_venda, 'YYYY-MM-DD') as data_venda, c.nome, c.cpf, ";

        sqlBase += " ca.valor_total, " +
                    " (ca.valor_total - v.valor_produto) as juros_compra, ";

        sqlBase  += " ca.valor_entrada, ca.qtd_parcela, " +
                " pa.valor as valor_parcela, " +
                " r.nome as rede, " +
                " COALESCE(l.nome_fantasia, l.razao_social) as nomeLoja ";

        String sql = " from venda v " +
                " join cliente c on c.id = v.cliente_id " +
                " join loja l on l.id = v.loja_id " +
                " join empresa e on e.id = l.empresa_id " +
                " join rede r on r.id = e.rede_id " +
                " join carne ca on ca.venda_id = v.id " +
                " join parcela pa on pa.carne_id = ca.id and pa.numero = 1" +
                " where 1=1 ";

        if (filtro.getLojaId() != null && filtro.getLojaId().intValue() != Constants.SELECT_TODAS) {
            sql += " and v.loja_id =:loja_id ";
        }

        sql += " and v.data_venda >= :dataInicio and v.data_venda <= :dataFim ";

        sql += " order by v.data_venda ";

        Query query = entityManager.createNativeQuery(sqlBase + sql);
        addFilters(filtro, query);

        List<ComprasDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            ComprasDTO comprasClientes = new ComprasDTO();
            try {
                //comprasClientes.setDataVenda(DateUtils.getDiaMesAnoPortugues((String) line[0]));
                comprasClientes.setNomeCliente((String) line[1]);
                comprasClientes.setCpf((String) line[2]);
                comprasClientes.setValorProduto((BigDecimal) line[3]);
                comprasClientes.setJurosCompra((BigDecimal) line[4]);
                comprasClientes.setValorEntrada((BigDecimal) line[5]);
                comprasClientes.setQtParcela((Integer) line[6]);
                comprasClientes.setValorParcela((BigDecimal) line[7]);
                comprasClientes.setNomeRede((String)line[8]);
                comprasClientes.setNomeLoja((String) line[9]);
                listaResultados.add(comprasClientes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaResultados;
    }

    private void addFilters(FiltroRelatorioDTO filtro, Query query) {
        if (filtro.getLojaId() != null && filtro.getLojaId().intValue() != Constants.SELECT_TODAS) {
            query.setParameter("loja_id", filtro.getLojaId());
        }

        Calendar dataInicio = Calendar.getInstance();
        Calendar dataFim = Calendar.getInstance();
        dataInicio.add(Calendar.DATE, -360);
        if (filtro.getDataInicio() != null) {
            dataInicio = DateUtils.zeraHorario(filtro.getDataInicio());
        }
        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            dataFim = DateUtils.setUltimaHoraDoDia(filtro.getDataFim());
        }
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);
    }
}