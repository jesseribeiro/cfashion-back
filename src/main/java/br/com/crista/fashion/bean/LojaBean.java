package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.StatusLoja;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "Loja")
public class LojaBean extends GenericBean {

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private StatusLoja status;

    private String site;
    private String email;
    private String whatsapp;

    @Column(name = "pessoa_contato")
    private String pessoaContato;

    @Column(name = "tel_comercial")
    private String telComercial;

    @Column(name = "fax_comercial")
    private String faxComercial;

    @Column(name = "tel_0800")
    private String tel0800;

    @Column(name = "chave_tef")
    private String chaveTEF;

    @Column(name = "is_emitir_cartao_loja", columnDefinition = "boolean DEFAULT false")
    private Boolean isEmitirCartaoLoja;

    @Column(name = "is_enviar_sms_boas_vindas", columnDefinition = "boolean DEFAULT false")
    private Boolean isEnviarSmsBoasVindas;

    @Column(name = "is_permite_pagto_loja", columnDefinition = "boolean DEFAULT false")
    private Boolean isPermitePagtoLoja;

    @Column(name = "is_permite_autorizacao_compra_app", columnDefinition = "boolean DEFAULT false")
    private Boolean IsPermiteAutorizacaoCompraApp;

    @Column(name = "possui_limite_exclusivo", columnDefinition = "boolean DEFAULT false")
    private Boolean IsPossuiLimiteExclusivo;

    @Column(name = "crediario_proprio", columnDefinition = "boolean DEFAULT false")
    private Boolean crediarioProprio;

    private String observacoes;

    @Column(name = "campanha_ativa", columnDefinition = "boolean DEFAULT false")
    private Boolean campanhaAtiva;

    private Long campanhaQtdVendas;
    private Long campanhaQtdDiasPrimeiraParcela;

    @JsonIgnore
    @JoinColumn(name = "endereco_id")
    @ManyToOne
    private LojaEnderecoBean endereco;

    public String getNomeLoja() {
        return (nomeFantasia != null && !nomeFantasia.isEmpty()) ? nomeFantasia : razaoSocial;
    }

    public boolean getIsEmitirCartaoLoja(){
        return isEmitirCartaoLoja == null ? false : isEmitirCartaoLoja;
    }

    public boolean getIsEnviarSmsBoasVindas(){
        return isEnviarSmsBoasVindas == null ? false : isEnviarSmsBoasVindas;
    }

    public boolean getIsPermitePagtoLoja(){
        return isPermitePagtoLoja == null ? false : isPermitePagtoLoja;
    }

    public boolean getIsPermiteAutorizacaoCompraApp(){
        return IsPermiteAutorizacaoCompraApp == null ? false : IsPermiteAutorizacaoCompraApp;
    }

    public boolean getIsPossuiLimiteExclusivo(){
        return IsPossuiLimiteExclusivo == null ? false : IsPossuiLimiteExclusivo;
    }

    public boolean getCampanhaAtiva(){
        return campanhaAtiva == null ? false : campanhaAtiva;
    }
}
