package br.com.crista.fashion.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.text.Normalizer;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String removeAcentos(String texto) {

        if (nonNull(texto)) {

            return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
        }

        return "";
    }

    public static String removePontos(String texto) {

        if (nonNull(texto)) {

            return texto.replaceAll("\\.","");
        }

        return "";
    }

    public static String desformataCpfCnpj(String cpfCnpj) {

        if (isNull(cpfCnpj) || cpfCnpj.isEmpty()) {

            return cpfCnpj;
        }

        return cpfCnpj.replaceAll("\\D+","");
    }

    public static String inserirMascaraCpfCnpj(final String cpfCnpj) {

        if (isNull(cpfCnpj) || cpfCnpj.isEmpty()) {

            return cpfCnpj;
        }

        try {

            String cpfCnpjSemMask = desformataCpfCnpj(cpfCnpj);

            if (cpfCnpjSemMask.length() < 11) {

                return cpfCnpj;
            }

            MaskFormatter mask = new MaskFormatter();
            mask.setValueContainsLiteralCharacters(false);

            if (cpfCnpjSemMask.length() == 11) {

                mask.setMask("###.###.###-##");
                return mask.valueToString(cpfCnpjSemMask);

            } else if (cpfCnpjSemMask.length() == 14) {

                mask.setMask("##.###.###/####-##");
                return mask.valueToString(cpfCnpjSemMask);

            } else {

                throw new RuntimeException("Erro ao inserir mascara no CNPJ: Tamanho inválido");
            }

        } catch (ParseException var2) {

            throw new RuntimeException("Erro ao inserir mascara no CPF: Tamanho inválido");
        }
    }
}
