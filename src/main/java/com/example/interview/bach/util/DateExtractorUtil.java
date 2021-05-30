package com.example.interview.bach.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateExtractorUtil {


  public static LocalDate extractDateWithSuffix(String dateWithSuffix) {
    String clearDateFormat = dateWithSuffix.replaceAll("(?<=\\d)(st|nd|rd|th)", "");
    return LocalDate.parse(clearDateFormat, DateTimeFormatter.ofPattern("MMMM d, yyyy"));
  }

}
