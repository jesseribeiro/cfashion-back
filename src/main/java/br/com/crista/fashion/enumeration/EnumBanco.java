package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumBanco {
    BRASIL("001 - Banco do Brasil S.A.", 1, "http://www.bb.com.br", "BB"),
    ITAU("341 - Itaú Unibanco S.A.", 341, "http://www.itau.com.br", "Itaú"),
    CAIXA("104 - Caixa Econômica Federal", 104, "http://www.caixa.gov.br", "Caixa"),
    BRADESCO("237 - Banco Bradesco S.A.", 237, "http://www.bradesco.com.br", "Bradesco"),
    SANTANDER("033 - Banco Santander S.A.",33,"https://www.santander.com.br","Santander"),
    NUBANK("260 - Nubank",260,"https://nubank.com.br","Nubank"),
    UNICRED("136 - Banco Unicred", 136, "http://www.unicred.com.br", "Unicred"),
    SICOOB("756 - Banco Cooperativo do Brasil S.A. - SICOOB",756,"https://www.sicoob.com.br","Sicoob"),
    SICREDI("748 - Banco Cooperativo Sicredi S.A. - SICREDI",748,"http://www.sicredi.com.br","Sicredi"),
    RENNER("654 - Banco A.J.Renner S.A.", 654, "http://www.bancorenner.com.br ", "Renner"),
    ABC("246 - Banco ABC Brasil S.A.", 246, "http://www.abcbrasil.com.br", "ABC"),
    AMRO("356 - Banco ABN AMRO Real S.A.", 356, "http://www.abnamro.com.br", "AMRO"),
    ALFA("025 - Banco Alfa S.A.", 25, "http://www.bancoalfa.com.br", "Alfa"),
    ALVORADA("641 - Banco Alvorada S.A.", 641, "", "Alvorada"),
    ARBI("213 - Banco Arbi S.A.", 213, "http://www.bancoarbi.com.br", "Arbi"),
    C6BANK("336 - C6 Bank S.A.", 336, "http://www.c6bank.com.br","C6 S.A."),
    AMAZONIA("003 - Banco da Amazônia S.A.", 3, "http://www.bancoamazonia.com.br", "Amazônia"),
    NORDESTE("004 - Banco do Nordeste do Brasil S.A.", 4, "http://www.banconordeste.gov.br", "Nordeste"),
    IBI("063 - Ibibank S.A. - Banco Múltiplo", 63, "", "Ibi"),
    ORIGINAL("212 - Banco Original", 212, "http://www.original.com.br", "Original"),
    BANCO1("062 - Banco1.Net S.A.", 62, "http://www.banestes.com.br", "Banco1"),
    BANDEIRANTES("230 - Banco Bandeirantes S.A.", 230, "http://www.bandeirantes.com.br", "Bandeirantes"),
    BANDEPE("024 - Banco de Pernambuco S.A. - BANDEPE", 24, "http://www.bandepe.com.br", "Bandepe"),
    BANEB("028 - Banco Baneb S.A.", 28, "http://www.baneb.com.br", "Baneb"),
    BANERJ("029 - Banco Banerj S.A.", 29, "http://www.banerj.com.br", "Banerj"),
    BANESE("021 - BANESTES S.A. - Banco do Estado do Espírito Santo", 21, "", "Banese"),
    BANESTADO("038 - Banco Banestado S.A.", 38, "", "Banestado"),
    BANIF("719 - Banco Banif Primus S.A.", 719, "http://www.bancobanif.com.br","Banif"),
    AMERICA("148 - Bank of America", 148, "http://www.bankofamerica.com", "America"),
    BOSTON("774 - BankBoston N.A.", 774, "http://www.bankboston.com.br", "Boston"),
    BOSTONMULT("479 - BankBoston Banco Múltiplo S.A.", 479, "", "BostonMult"),
    BANPARA("037 - Banco do Estado do Pará S.A.", 37, "http://www.banpara.b.br", "Para"),
    BANRISUL("041 - Banco do Estado do Rio Grande do Sul S.A.", 41, "http://www.banrisul.com.br", "Banrisul"),
    BARCLAYS("740 - Banco Barclays e Galícia S.A.", 740, "http://www.barclays.com", "Barclays"),
    BBM("107 - Banco BBM S.A.", 107, "http://www.bbmbank.com.br", "BBM"),
    CEARA("035 - Banco do Estado do Ceará S.A.", 35, "", "Ceara"),
    GOIAS("031 - Banco do Estado de Goiás S.A. - BEG", 31, "", "Goias"),
    MARANHAO("036 - Banco do Estado do Maranhão S.A.", 36, "http://www.bemnet.com.br", "Maranhao"),
    BEMGE("048 - Banco Bemge S.A.", 48, "http://www.bemge.com.br", "Bemge"),
    PIAUI("039 - Banco do Estado do Piauí S.A.", 39, "", "Piaui"),
    BESC("027 - Banco do Estado de Santa Catarina S.A.", 27, "http://www.besc.com.br", "BESC"),
    BGN("739 - Banco BGN S.A.", 739, "", "BGN"),
    BICBANCO("320 - Banco Industrial e Comercial S.A.", 320, "http://www.bicbanco.com.br", "BicBanco"),
    BMC("394 - Banco BMC S.A.", 394, "http://www.bmc.com.br", "BMC"),
    BMG("318 - Banco BMG S.A.", 318, "http://www.bancobmg.com.br", "BMG"),
    BNL("116 - Banco BNL do Brasil S.A.", 116, "http://www.bnl.com.br", "BNL"),
    BNP("752 - Banco BNP Paribas Brasil S.A.", 752, "http://www.bnpparibas.com.br", "Paribas"),
    BOAVISTA("248 - Banco Boavista Interatlântico S.A.", 248, "", "Boavista"),
    BONSUCESSO("218 - Banco Bonsucesso S.A.", 218, "http://www.bancobs2.com.br", "Bonsucesso"),
    BPN("888 - BPN Banco Múltiplo S.A.", 888, "", "BPN"),
    BRASCAN("225- Banco Brascan S.A.", 225, "http://www.bancobrascan.com.br", "Brascan"),
    BRASILIA("070 - BRB - Banco de Brasília S.A.", 70, "http://www.brb.com.br", "Brasilia"),
    BVA("044 - Banco BVA S.A.", 44,"http://www.bancobva.com.br", "BVA"),
    CACIQUE("263 - Banco Cacique S.A.",263, "http://www.societegeneralebrasil.com.br", "Cacique"),
    CALYON("222 - Banco Clayon Brasil S.A.",222, "http://www.calyon.com.br","Calyon"),
    CAPITAL("412 - Banco Capital S.A.",412, "http://www.bancocapital.com.br","Capital"),
    CARGIL("040 - Banco Cargill S.A.",40,"http://www.bancocargill.com.br","Cargill"),
    CITIBANK("745 - Banco Citibank S.A.",745,"http://www.corporateportal.brazil.citibank.com","Citibank"),
    CITIBANKNA("477 - Citibank N.A.",477,"http://www.bnamericas.com","CitibankNA"),
    CLASSICO("241 - Banco Clássico S.A.",241,"https://www.bancoclassico.com.br","Classico"),
    SUDAMERIS("215 - Banco Comercial e de Investimentos Sudameris S.A",215,"","Sudameris"),
    URUGUAI("753 - Banco Comercial Uruguai S.A.",753,"","Uruguai"),
    AILOS("085 - Cooperativa Central Ailos",85,"https://www.ailos.coop.br","Ailos"),
    CORA("403 - Cora SDC S.A.",403,"https://www.cora.com.br","Cora"),
    CREDIBEL("721 - Banco Credibel S.A.",721,"https://www.credibel.com.br","Credibel"),
    CREDISAN("089 - Credisan", 89, "https://www.portal.credisan.com.br","Credisan"),
    SUISSE("505 - Banco Credit Suisse First Boston Garantia S.A.", 505, "", "Suisse"),
    CRESOL("133 - Cresol - Cooperativa de Crédito",133,"https://www.cresol.com.br","Cresol"),
    CRUZEIRO("229 - Banco Cruzeiro do Sul S.A.",229,"https://www.bcsul.com.br","Cruzeiro"),
    DAYCOVAL("707 - Banco Daycoval S.A.",707,"https://www.daycoval.com.br","Daycoval"),
    DEUTSCHE("487 - Deutsche Bank S.A. - Banco Alemão",487,"https://www.deutsche-bank.com.br","Deutsche"),
    DIBENS("214 - Banco Dibens S.A.",214,"https://www.bancodibens.com.br","Dibens"),
    DRESDNER("210 - Dresdner Bank Lateinamerika AG",210,"https://www.privatebanking.com","Dresdner"),
    DRESDNERBR("751 - Dresdner Bank Brasil S.A. - Banco Múltiplo",751,"","DresdnerBR"),
    EMBLEMA("743 - Banco Emblema S.A.",743,"","Emblema"),
    FATOR("265 - Banco Fator S.A.",265,"https://www.fundosplural.com.br","Fator"),
    FIBRA("224 - Banco Fibra S.A.",224,"https://www.bancofibra.com.br","Fibra"),
    FICSA("626 - Banco Ficsa S.A.",626,"https://www.ficsa.com.br","Ficsa"),
    FINASA("175 - Continental Banco S.A.",175,"https://www.continentalbanco.com","Finasa"),
    FININVEST("252 - Banco Fininvest S.A.",252,"","Fininvest"),
    GECAPITAL("233 - Banco GE Capital S.A.",233,"","GECapital"),
    GERDAU("734 - Banco Gerdau S.A.",734,"https://www.ri.gerdau.com","Gerdau"),
    GERENCIANET("364 - Gerencianet",364,"https://gerencianet.com.br","Gerencianet"),
    GOLDMAN("064 - The Goldman Sachs Group",64,"https://www.goldmansachs.com","Goldman"),
    GUANABARA("612 - Banco Guanabara S.A.",612,"","Guanabara"),
    INDUSTRIAL("604 - Banco Industrial do Brasil S.A.",604,"https://www.bib.com.br","Industrial"),
    INDUSVAL("653 - Banco Indusval S.A.",653,"https://www.indusval.com.br","Indusval"),
    ING("492 - ING Bank N.V.",492,"https://www.ing-barings.com.br","ING"),
    INTER("077 - Banco Inter S.A.",77,"https://www.bancointer.com.br","Inter"),
    AMERICAN("204 - Banco Inter American Express S.A.",204,"https://www.iamex.com.br","American"),
    INTERCAP("630 - Banco Intercap S.A.",630,"https://www.intercap.com.br","Intercap"),
    INVESTCRED("249 - Banco Investcred S.A.",249,"","Investcred"),
    ITAUBBA("184 - Banco BBA - CREDITANSTALT S.A.",184,"https://www.bba.com.br","ItauBBA"),
    ITAUHOLDING("652 - Banco Itaú Holding Financeira S.A.",652,"","ItauHolding"),
    JPMORGAN("376 - Banco J.P. Morgan S.A.",376,"https://www.jpmorgan.com.br","JPMorgan"),
    MORGANCHASE("488 - Morgan Guaranty Trust Company of New York",488,"","Morgan"),
    JSAFRA("042 - Banco J. Safra S.A.",42,"https://www.safra.com.br","Safra"),
    JOHNDEERE("217 - Banco John Deere S.A.",217,"https://www.deere.com.br","JohnDeere"),
    KEB("757 - Banco KEB Hana do Brasil S.A.",757,"https://bancokebhana.com.br","KEB"),
    HSBC("399 - HSBC Bank Brasil S.A. - Banco Múltiplo",399,"https://www.hsbc.com.br","HSBC"),
    ARGENTINA("300 - Banco de La Nacion Argentina",300,"https://www.bna.com.ar","Argentina"),
    PROVINCIA("495 - Banco de La Provincia de Buenos Aires",495,"https://www.bancoprovincia.com.ar","Provincia"),
    ORIENTAL("494 - Banco de La Republica Oriental del Uruguay",494,"https://www.brou.com.uy","Oriental"),
    LEMON("065 - Lemon Bank Banco MÚltiplo S.A.",65,"","Lemon"),
    LLOYDS("472 - Lloyds TSB Bank PLC.",472,"https://www.lloydstsb.com.br","Lloyds"),
    LUSO("600 - Banco Luso Brasileiro S.A.",600,"https://www.bancoluso.com.br","Luso"),
    MULTISTOCK("243 - Banco Multi Stock S.A.",243,"","Multistock"),
    MERCADOPAGO("323 - Mercado Pago",323,"https://www.mercadopago.com.br","MercadoPGO"),
    MERCANTILSP("392 - Banco Mercantil de São Paulo S.A.",392,"https://www.finasa.com.br","MercantilSP"),
    MERCANTILBR("389 - Banco Mercantil do Brasil S.A.",389,"https://www.mercantil.com.br","MercantilBR"),
    MERRILL("755 - Banco Merrill Lynch S.A.",755,"https://www.ml.com","Merrill"),
    MODAL("746 - Banco Modal S.A.",746,"https://www.modalmais.com.br","Modal"),
    MORADA("738 - Banco Morada S.A.",738,"https://www.bancomorada.com.br","Morada"),
    MORGAN("066 - Banco Morgan Stanley S.A.",66,"https://www.morganstanley.com.br","Stanley"),
    POTTENCIAL("735 - Banco Pottencial S.A.",735,"https://www.pottencial.com.br","Pottencial"),
    NOSSACAIXA("151 - Banco Nossa Caixa S.A.",151,"","NossaCaixa"),
    OPPORTUNITY("045 - Banco Opportunity S.A.",45,"https://www.opportunity.com.br","Opportunity"),
    PACTUAL("208 - Banco Pactual S.A.",208,"http://www.pactual.com.br","Pactual"),
    PAGSEGURO("290 - PagSeguro",290,"https://pagseguro.uol.com.br","PagSeguro"),
    PANAMERICANO("623 - Banco Panamericano S.A.",623,"http://www.panamericano.com.br","Panamericano"),
    PARANA("254 - Paraná Banco S.A.", 254,"http://www.paranabanco.com.br","Parana"),
    PAULISTA("611 - Banco Paulista S.A.",611,"http://www.bancopaulista.com.br","Paulista"),
    PEBB("650 - Banco Pebb S.A.",650,"","Pebb"),
    PECUNIA("613 - Banco Pecúnia S.A.",613,"","Pecunia"),
    PINE("643 - Banco Pine S.A.",643,"http://www.bancopine.com.br","Pine"),
    PORTOSEGURO("724 - Financiamento Porto Seguro",724,"http://www.portoseguro.com.br","PortoSeguro"),
    PROSPER("638 - Banco Prosper S.A.",638,"","Prosper"),
    RABOBANK("747 - Banco Rabobank International Brasil S.A.",747,"http://www.rabobank.com","Rabobank"),
    RENDIMENTO("633 - Banco Rendimento S.A.",633,"https://www.rendimento.com.br","Rendimento"),
    RIBEIRAO("741 - Banco Ribeirão Preto S.A.",741,"https://brp.com.br","RibeiraoPreto"),
    RURAL("453 - Banco Rural S.A.",453,"http://www.rural.com.br","Rural"),
    RURALMAIS("072 - Banco Rural Mais S.A.",72,"","RuralMais"),
    SAFRA("422 - Banco Safra S.A.",422,"http://www.safra.com.br","Safra"),
    SANTANDERBR("353 - Banco Santander Brasil S.A.",353,"http://www.santander.com.br","SantanderBR"),
    SANTANDERANT("351 - Banco Santander de Negócios S.A.",351,"http://www.santander.com.br","SantanderANT"),
    MERIDIONAL("008 - Banco Santander Meridional S.A.",8,"http://www.meridional.com.br","Meridional"),
    SANTOS("702 - Banco Santos S.A.",702,"http://www.bancosantos.com.br","Santos"),
    SCHAHIN("250 - Banco Schahin S.A.",250,"http://www.schahin.com.br","Schahin"),
    MERCANTIL("749 - BR Banco Mercantil S.A.",749,"https://mercantildobrasil.com.br","Mercantil"),
    SOFISA("637 - Banco Sofisa S.A.",637,"http://www.sofisa.com.br","Sofisa"),
    SOGERAL("366 - Banco Sogeral S.A.",366,"http://www.sogeral.com.br","Sogeral"),
    STONE("197 - Banco Stone S.A.",197,"","Stone"),
    SUMITOMO("464 - Banco Sumitomo Brasileiro S.A.",464,"http://www.sumitomobank.co.jp","Sumimoto"),
    TOKYO("456 - Banco de Tokyo-Mitsubishi Brasil S.A.",456,"www.btm.com.br","Tokyo"),
    TOPAZIO("082 - Banco Topázio S.A.",82,"https://www.bancotopazio.com.br","Topazio"),
    TRIBANCO("634 - Banco Triângulo S.A.",634,"www.tribanco.com.br","Tribanco"),
    UBS("247 - Banco UBS Warburg S.A.",247,"http://www.ubsw.com","UBS"),
    UNIBANCO("409 - UNIBANCO - União de Bancos Brasileiros S.A.",409,"http://www.unibanco.com.br","Unibanco"),
    UNION("493 - Banco Union - Brasil S.A.",493,"http://www.bancunion.com.br","Union"),
    UNIPRIME("084 - Uniprime - Cooperativa de Crédito do Norte do Paraná",84,"https://www.uniprimebr.com.br","Uniprime"),
    UNIPRIMECT("099 - Uniprime Central",99,"","UniprimeCT"),
    VOTORANTIM("655 - Banco Votorantim S.A.",655,"http://www.bancovotorantim.com.br","Votorantim"),
    VR("610 - Banco VR S.A.",610,"https://www.bancovr.com.br","VR"),
    WESTLB("370 - Banco Europeu para a America Latina (BEAL) S.A.",370,"http://www.westlb.com","WestLB"),
    ZOGBI("219 - Banco Zogbi S.A.",219,"http://www.bancozogbi.com.br","Zogbi"),
    EM_LOJA("Em Loja",0,"", ""),
    NO_CARTAO("No Cartão de Crédito",9999,"", "");

    private String label;
    private int codigo;
    private String site;
    private String sigla;

    EnumBanco(String label, int codigo, String site, String sigla){
        this.label = label;
        this.codigo = codigo;
        this.site = site;
        this.sigla = sigla;
    }

    public static List<LabelDescricaoDTO> getLabels() {
        return Arrays.stream(values())
                .filter(enumBanco -> enumBanco.getCodigo() != 0 && enumBanco.getCodigo() != 9999)
                .map(banco -> new LabelDescricaoDTO(banco.name(), banco.getLabel())).collect(Collectors.toList());
    }

    public static EnumBanco getDescricao(int value) {
        for (EnumBanco banco : values()) {
            if (banco.getCodigo() == value) {
                return banco;
            }
        }
        return null;
    }
}
