package de.bund.bva.isyfact.terminfindung.gui.util;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;

public class StringToUuidConverter implements Converter<String, UUID> {

    public UUID convert(String source) {
        return UUID.fromString(source);
    }

}
