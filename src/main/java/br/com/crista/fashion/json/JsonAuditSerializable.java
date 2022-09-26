package br.com.crista.fashion.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.json.JSONObject;

public class JsonAuditSerializable extends StdConverter<JSONObject, String> {

    @Override
    public String convert(JSONObject value) {
        String str = value.toString();
        return str;
    }
}
