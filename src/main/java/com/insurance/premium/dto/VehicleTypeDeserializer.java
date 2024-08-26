package com.insurance.premium.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.insurance.premium.enums.VehicleType;

import java.io.IOException;

/**
 * Custom Deserializer für das VehicleType-Enum.
 * Wandelt den JSON-String in das entsprechende VehicleType-Enum um,
 * wobei Groß- und Kleinschreibung ignoriert wird.
 * Wenn der String nicht in ein bekanntes VehicleType-Enum konvertiert werden kann,
 * wird `DEFAULT_FACTOR` zurückgegeben.
 */
public class VehicleTypeDeserializer extends JsonDeserializer<VehicleType> {

    /**
     * Deserialisiert den JSON-String in ein VehicleType-Enum.
     *
     * @param p     Der JSON-Parser, der den String-Wert liefert.
     * @param ctxt  Der Deserialisierungskontext.
     * @return Das entsprechende VehicleType-Enum oder `DEFAULT_FACTOR`, wenn der String ungültig ist.
     * @throws IOException Wenn ein Fehler während der Deserialisierung auftritt.
     */
    @Override
    public VehicleType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();
        try {
            return VehicleType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return VehicleType.DEFAULT_FACTOR;
        }
    }
}