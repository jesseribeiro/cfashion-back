package br.com.crista.fashion.json;

import br.com.crista.fashion.utils.DateUtils;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarListDeserializerJson extends StdConverter<List<Long>, List<Calendar>> {
    @Override
    public List<Calendar> convert(List<Long> list) {
        if(list != null && !list.isEmpty()) {
            return list.
            stream()
            .map(s -> DateUtils.getDiaMesAno(new Date(s)))
            .collect(Collectors.toList());
        }
        return null;
    }
}