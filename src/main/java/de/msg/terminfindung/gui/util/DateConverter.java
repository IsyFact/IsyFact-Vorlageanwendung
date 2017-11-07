package de.msg.terminfindung.gui.util;

import javax.faces.convert.DateTimeConverter;

/**
 * Einfacher Konverter für Datumsangaben
 *
 * @author msg systems ag, Björn Saxe
 */
public class DateConverter extends DateTimeConverter {

	private static String format = "dd.MM.yyyy";
	
	public DateConverter()
	{
		setPattern(format);
	}
}
