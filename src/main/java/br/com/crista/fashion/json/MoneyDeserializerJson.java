package br.com.crista.fashion.json;

import br.com.crista.fashion.utils.MathUtils;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyDeserializerJson extends StdConverter<String, BigDecimal> {
    @Override
    public BigDecimal convert(String value) {
        if(value == null || value.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal bd = MathUtils.convertStringToBigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd;
    }
}
