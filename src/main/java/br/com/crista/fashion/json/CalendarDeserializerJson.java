package br.com.crista.fashion.json;

import br.com.crista.fashion.utils.DateUtils;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Calendar;

public class CalendarDeserializerJson extends StdConverter<String, Calendar> {
    @Override
    public Calendar convert(String value) {
        return DateUtils.getDiaMesAno(value);
    }
}