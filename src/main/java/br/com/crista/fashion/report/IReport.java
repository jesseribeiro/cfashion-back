package br.com.crista.fashion.report;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public abstract class IReport {

	private Boolean flgLogo;

	private Map<String, Object> parametros;

	public abstract Map<String, Object> getParametrosReport();

	public abstract String getFileNameJrxml();

	public IReport(){
		this(true);
	}

	public IReport(Boolean flgLogo){
		this.flgLogo = flgLogo;
		this.parametros = new HashMap<>();

		if(flgLogo) {
			this.addLogo();
		}
	}

	private void addLogo() {
		try {
			//ImageIcon imageIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/logo.png")));
			BufferedImage image = ImageIO.read(getClass().getResource("/logo.png"));
			parametros.put("logo", image);
		} catch (IOException e) {
			log.error("Erro ao buscar logo para add report");
		}
	}

	public JasperReport compile() throws JRException {
		InputStream inputStream = getClass().getResourceAsStream(getFileNameJrxml());
		return JasperCompileManager.compileReport(inputStream);
	}
}
