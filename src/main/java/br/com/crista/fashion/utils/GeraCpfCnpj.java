package br.com.crista.fashion.utils;

import java.util.InputMismatchException;
import java.util.Random;

public class GeraCpfCnpj {

	private static final Random numeroAleatorio = new Random();

	private GeraCpfCnpj(){}

	private static int mod(int dividendo, int divisor) {
		return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
	}

	public static String cpf(boolean comPontos) {
		int n1 = numeroAleatorio.nextInt(9);
		int n2 = numeroAleatorio.nextInt(9);
		int n3 = numeroAleatorio.nextInt(9);
		int n4 = numeroAleatorio.nextInt(9);
		int n5 = numeroAleatorio.nextInt(9);
		int n6 = numeroAleatorio.nextInt(9);
		int n7 = numeroAleatorio.nextInt(9);
		int n8 = numeroAleatorio.nextInt(9);
		int n9 = numeroAleatorio.nextInt(9);
		int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10) {
			d1 = 0;
		}

		int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

		d2 = 11 - (mod(d2, 11));

		String retorno = null;

		if (d2 >= 10) {
			d2 = 0;
		}

		if (comPontos) {
			retorno = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
		} else {
			retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;
		}

		return retorno;
	}

	public static String cnpj(boolean comPontos) {
		int n1 = numeroAleatorio.nextInt(9);
		int n2 = numeroAleatorio.nextInt(9);
		int n3 = numeroAleatorio.nextInt(9);
		int n4 = numeroAleatorio.nextInt(9);
		int n5 = numeroAleatorio.nextInt(9);
		int n6 = numeroAleatorio.nextInt(9);
		int n7 = numeroAleatorio.nextInt(9);
		int n8 = numeroAleatorio.nextInt(9);
		int n9 = 0;
		int n10 = 0;
		int n11 = 0;
		int n12 = 1;
		int d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4 + n1 * 5;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4 + n2 * 5 + n1 * 6;

		d2 = 11 - (mod(d2, 11));

		if (d2 >= 10)
			d2 = 0;

		String retorno = null;

		if (comPontos)
			retorno = "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "/" + n9 + n10 + n11 + n12 + "-" + d1 + d2;
		else
			retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + d1 + d2;

		return retorno;
	}


	public static String rg(boolean comPontos) {
		String numeroGerado;
		//numeros gerados
		int n1 = numeroAleatorio.nextInt(10);
		int n2 = numeroAleatorio.nextInt(10);
		int n3 = numeroAleatorio.nextInt(10);
		int n4 = numeroAleatorio.nextInt(10);
		int n5 = numeroAleatorio.nextInt(10);
		int n6 = numeroAleatorio.nextInt(10);
		int n7 = numeroAleatorio.nextInt(10);
		int n8 = numeroAleatorio.nextInt(10);
		int n9 = numeroAleatorio.nextInt(10);

	    if (comPontos)
            numeroGerado = "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "-" + n9;
        else
            numeroGerado = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9;

		return numeroGerado;
	}

	public static boolean isCPF(final String cpf) {
		
		String cpfDesformatado = removeCaracteresEspeciais(cpf);
		
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (cpfDesformatado.equals("00000000000") || cpfDesformatado.equals("11111111111") || cpfDesformatado.equals("22222222222") || cpfDesformatado.equals("33333333333") || cpfDesformatado.equals("44444444444") || cpfDesformatado.equals("55555555555") || cpfDesformatado.equals("66666666666") || cpfDesformatado.equals("77777777777") || cpfDesformatado.equals("88888888888") || cpfDesformatado.equals("99999999999") || (cpfDesformatado.length() != 11))
			return (false);

		char dig10;
		char dig11;
		int sm;
		int i;
		int r;
		int num;
		int peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0        
				// (48 eh a posicao de '0' na tabela ASCII)        
				num = (cpfDesformatado.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (cpfDesformatado.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == cpfDesformatado.charAt(9)) && (dig11 == cpfDesformatado.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCNPJ(final String cnpj) {
		
		String cnpjDesformatado = removeCaracteresEspeciais(cnpj);
		
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (isCnpjSequencial(cnpjDesformatado))
			return (false);

		char dig13 = '0';
		char dig14 = '0';
		int sm;
		int i;
		int r;
		int num;
		int peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (cnpjDesformatado.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r != 0) && (r != 1)) {
				dig13 = (char) ((11 - r) + 48);
			}

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (cnpjDesformatado.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r != 0) && (r != 1)) {
				dig14 = (char) ((11 - r) + 48);
			}

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == cnpjDesformatado.charAt(12)) && (dig14 == cnpjDesformatado.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	private static boolean isCnpjSequencial(String cnpjDesformatado) {
		return cnpjDesformatado.equals("00000000000000") ||
				cnpjDesformatado.equals("11111111111111") ||
				cnpjDesformatado.equals("22222222222222") ||
				cnpjDesformatado.equals("33333333333333") ||
				cnpjDesformatado.equals("44444444444444") ||
				cnpjDesformatado.equals("55555555555555") ||
				cnpjDesformatado.equals("66666666666666") ||
				cnpjDesformatado.equals("77777777777777") ||
				cnpjDesformatado.equals("88888888888888") ||
				cnpjDesformatado.equals("99999999999999") ||
				(cnpjDesformatado.length() != 14);
	}

	private static String removeCaracteresEspeciais(String doc) {
		if (doc.contains(".")) {
			doc = doc.replace(".", "");
		}
		if (doc.contains("-")) {
			doc = doc.replace("-", "");
		}
		if (doc.contains("/")) {
			doc = doc.replace("/", "");
		}
		return doc;
	}

	public static String imprimeCNPJ(final String cnpj) {
		// máscara do CNPJ: 99.999.999.9999-99
		return (cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "." + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14));
	}
}