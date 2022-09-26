package br.com.crista.fashion.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Setter
@Getter
public class CarneReportDTO {

	private String nomeLoja;
	private String telefoneLoja;
	private String enderecoLoja;
	private String cidadeLoja;
	private String cep;
	private String cnpj;

	private Long carneId;
	private Calendar dataVenda;
	private Long autorizacaoId;
	private Integer qtdParcelas;
	private BigDecimal valorParcela;
	private BigDecimal valorVenda;

	private List<ParcelaReportDTO> parcelas;

	private String nomeCliente;
	private String cpf;

	private String telefoneConfig;

	private String nomeProduto;


    /*private String peMultaMoratoria;
    private String nomeVendedor;
    private String telefoneCliente;
    private String nuDiasAtraso;
	private String cidade;
	private String endereco;
	private String peCompensatoria;
	private String nuContrato;
	private String clienteId;
	private String vlTotal;
	private String peComissaoPermanencia;*/

	public static CarneReportDTO mock() {
		CarneReportDTO dto = new CarneReportDTO();
		List<ParcelaReportDTO> parcelas = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			int nuParcela = i+1;
			ParcelaReportDTO parcelaDTO = new ParcelaReportDTO();
			parcelaDTO.setDataVencimento( nuParcela > 9 ? "05/"+nuParcela+"/2020" : "05/0"+nuParcela+"/2020");
			parcelaDTO.setNuAutenticacaoLoja("544646351213"+i);
			parcelaDTO.setNuParcela(+nuParcela);
			parcelaDTO.setPlano("10");
			parcelaDTO.setVlParcela("95,90");
			parcelas.add(parcelaDTO);
		}
		//dto.setCidade("Estreito		Florianopolis");
		//dto.setClienteId("00001");
		dto.setCnpj("13.716.649/0005-89");
		dto.setCpf("118.310.509-60");
		//dto.setEndereco("Rua Sebastião Santos de Oliveira");
		dto.setNomeCliente("Maria Eduarda Antunes Farias");
		dto.setNomeLoja("Ótica Ferri LTDA. ME");
		//dto.setNomeVendedor("Ferri");
		//dto.setNuContrato("08098038");
		//dto.setNuDiasAtraso("90");
		//dto.setPeComissaoPermanencia("6%");
		//dto.setPeCompensatoria("10%");
		//dto.setPeMultaMoratoria("20%");
		//dto.setTelefoneCliente("123241534");
		dto.setTelefoneLoja("24134563454");
		//dto.setVlTotal("959,00 (novecentos e cinquenta e nove Reais");

		return dto;
	}
}
