package com.example.batch.util;

import org.apache.logging.log4j.util.Strings;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateExtractorUtil {


  public static LocalDate extractFromString(String dateWithSuffix) {
    if (dateWithSuffix.contains("st") || dateWithSuffix.contains("nd") || dateWithSuffix.contains("rd") || dateWithSuffix.contains("th")) {
      String clearDateFormat = dateWithSuffix.replaceAll("(?<=\\d)(st|nd|rd|th)", "");
      return LocalDate.parse(clearDateFormat, DateTimeFormatter.ofPattern("MMMM d, yyyy"));
    }
    return LocalDate.parse(dateWithSuffix, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

  }
}
