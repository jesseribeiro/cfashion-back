package br.com.crista.fashion.utils;

import javax.swing.text.MaskFormatter;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String removeAcentos(String texto) {
        if(texto != null) {
            return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
        }
        return "";
    }

    public static String desformataCpfCnpj(String cpfCnpj) {
        if( cpfCnpj == null || cpfCnpj.isEmpty()) {
            return cpfCnpj;
        }
        return cpfCnpj.replaceAll("\\D+","");
    }

    public static String inserirMascaraCpfCnpj(final String cpfCnpj) {
        if(cpfCnpj == null || cpfCnpj.isEmpty()) {
            return cpfCnpj;
        }

        try {
            String cpfCnpjSemMask = desformataCpfCnpj(cpfCnpj);
            if(cpfCnpjSemMask.length() < 11) {
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

    public static String extraiDDDTelefone(String telResidencial) {
        if( telResidencial == null || telResidencial.isEmpty()) {
            return telResidencial;
        }
        String saida = telResidencial.replaceAll("\\D+","");
        return saida.substring(0, 2);
    }

    public static String extraiTelefoneSemDDD(String telResidencial) {
        if( telResidencial == null || telResidencial.isEmpty()) {
            return telResidencial;
        }
        String saida = telResidencial.replaceAll("\\D+","");
        return saida.substring(2);
    }

    public static String primeiroNomeMsg (String nome){
        String primeiroNome;
        if(nome.equalsIgnoreCase("") || nome.equals(null)){
            primeiroNome = "Cliente";
        } else {
            try{
                primeiroNome = nome.substring(0, nome.indexOf(" "));
            }
            catch (Exception e){
                primeiroNome = nome;
            }
        }
        return primeiroNome;
    }

    public static String toTitleCase(String input) {
        StringBuffer strbf = new StringBuffer();
        Matcher match = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(input);
        while(match.find())
        {
            match.appendReplacement(strbf, match.group(1).toUpperCase() + match.group(2).toLowerCase());
        }
        input = match.appendTail(strbf).toString();
        return input;
    }


    public static String removeBarraJson(String str) {
        Integer begin = str.indexOf("\"") + 1;
        Integer last = str.lastIndexOf("\"");
        str = str.substring(begin,last);
        return str;
    }

    public static String removeStringNull(String str) {
        str = str.replaceAll("null","\"\"");
        return str;
    }
}
