package br.com.crista.fashion.json;

import br.com.crista.fashion.utils.MathUtils;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigDecimal;

public class MoneySerializerJson extends StdConverter<BigDecimal, String> {

    @Override
    public String convert(BigDecimal value) {
        return MathUtils.convertBigDecimalToString(value);
    }
}
