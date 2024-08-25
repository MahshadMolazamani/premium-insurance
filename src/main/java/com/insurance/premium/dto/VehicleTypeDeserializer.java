package com.insurance.premium.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.insurance.premium.enums.VehicleType;

import java.io.IOException;

public class VehicleTypeDeserializer extends JsonDeserializer<VehicleType> {

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