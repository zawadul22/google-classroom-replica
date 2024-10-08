package com.glcl.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ClassroomUtils {
  public String codeGenerate() {
    SecureRandom random = new SecureRandom();
    int randomNum = random.nextInt(0x100000);
    return String.format("%05X", randomNum).toLowerCase(Locale.ROOT);
  }
  public boolean dateValidator(String date) {
    try{
      DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
      LocalDateTime.parse(date, formatter);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  public boolean deadlineValidator(String createdAt, String deadline) {
    LocalDateTime start = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME);
    LocalDateTime end = LocalDateTime.parse(deadline, DateTimeFormatter.ISO_DATE_TIME);
    return end.isAfter(start);
  }
}
