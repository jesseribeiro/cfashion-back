CREATE VIEW view_cliente_primeira_fatura AS
    select
	cl.cliente_id, p.id, p.valor, p.data_vencimento, p.status
	from cliente_loja cl
	join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
	join carne cc on cc.venda_id = v.id
	join parcela p on p.carne_id = cc.id
	join (select cl.cliente_id, min(p.id) as parcela_id
		from cliente_loja cl
		join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
		join carne cc on cc.venda_id = v.id
		join parcela p on p.carne_id = cc.id
		group by cl.cliente_id
	) as primeira on primeira.parcela_id = p.id;
	
CREATE VIEW view_cliente_saldo_devedor AS
    select
	cl.cliente_id, sum(p.valor) as saldoDevedor
	from cliente_loja cl
	join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
	join carne cc on cc.venda_id = v.id
	join parcela p on p.carne_id = cc.id
	where
	p.data_vencimento < now()
	and p.status = 'NAO_PAGA'
	group by cl.cliente_id;

CREATE VIEW view_cliente_loja_saldo_devedor AS
    select
	cl.cliente_id, cl.loja_id, sum(p.valor) as saldoDevedor
	from cliente_loja cl
	join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
	join carne cc on cc.venda_id = v.id
	join parcela p on p.carne_id = cc.id
	where p.status = 'NAO_PAGA'
	group by cl.cliente_id, cl.loja_id;

CREATE VIEW view_cliente_loja_atraso AS
    select
    cl.cliente_id, cl.loja_id, max(DATE_PART('day', now()::timestamp - p.data_vencimento::timestamp)) as maior_atraso
    from cliente_loja cl
    join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
    join carne cc on cc.venda_id = v.id
    join parcela p on p.carne_id = cc.id
    where
    p.data_vencimento < now()
    and p.status = 'NAO_PAGA'
    group by cl.cliente_id, cl.loja_id;
	
CREATE VIEW view_cliente_em_atraso AS
    select
	cl.cliente_id, sum(DATE_PART('day', now()::timestamp - p.data_vencimento::timestamp)) as dias_atraso
	from cliente_loja cl
	join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
	join carne cc on cc.venda_id = v.id
	join parcela p on p.carne_id = cc.id
	where
	p.data_vencimento < now()
	and p.status = 'NAO_PAGA'
	group by cl.cliente_id;
	
CREATE VIEW view_cliente_em_atraso_acordo AS
    select
	cl.cliente_id, sum(DATE_PART('day', now()::timestamp - p.data_vencimento::timestamp)) as dias_atraso
	from cliente_loja cl
	join renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id
	join carne cc on cc.renegociacao_id = r.id
	join parcela p on p.carne_id = cc.id
	where
	p.data_vencimento < now()
	and p.status = 'NAO_PAGA'
	group by cl.cliente_id;

CREATE VIEW view_cliente_status_spc AS
    select
    h.cliente_id,
    h.operacao as status,
    h.data_cadastro
    from historico_clientespc h
    join (
    select h.cliente_id, max(h.id) as historico_id
    from historico_clientespc h
    group by h.cliente_id) as ultimo_historico on
    ultimo_historico.cliente_id = h.cliente_id
    and ultimo_historico.historico_id = h.id;


CREATE VIEW view_cliente_ultimo_pagamento AS
    select pg.id, pg.data_pagto, v.cliente_id, v.loja_id
	from pagamento pg
	join parcela p on p.id = pg.parcela_id
	join carne cc on cc.id = p.carne_id
	join venda v on v.id = cc.venda_id
	join (select v.cliente_id, max(pg.id) as id
		from pagamento pg
		join parcela p on p.id = pg.parcela_id
		join carne cc on cc.id = p.carne_id
		join venda v on v.id = cc.venda_id
		group by v.cliente_id) as ultimo_pagto on 
		ultimo_pagto.cliente_id = v.cliente_id
		and ultimo_pagto.id = pg.id

CREATE VIEW view_cliente_fatura_atual AS
    select
	cl.cliente_id, p.id, p.valor, p.data_vencimento, p.status, p.boleto_id
	from cliente_loja cl
	join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
	join carne cc on cc.venda_id = v.id
	join parcela p on p.carne_id = cc.id
	join (select cl.cliente_id, min(p.id) as parcela_id
		from cliente_loja cl
		join venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id
		join carne cc on cc.venda_id = v.id
		join parcela p on p.carne_id = cc.id and p.status = 'NAO_PAGA'
		group by cl.cliente_id
	) as primeira on primeira.parcela_id = p.id;

CREATE VIEW view_cliente_ultima_ocorrencia_cobranca AS
    select y.* from ocorrencia_cobranca y
    join (
    select max(id) as cobranca_id, oc.cliente_id, oc.usuario_id from ocorrencia_cobranca oc
    group by oc.cliente_id, oc.usuario_id) x on x.cobranca_id = y.id;