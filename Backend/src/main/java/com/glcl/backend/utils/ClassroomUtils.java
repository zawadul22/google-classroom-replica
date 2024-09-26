package com.glcl.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ClassroomUtils {
  public String codeGenerate() {
    SecureRandom random = new SecureRandom();
    int randomNum = random.nextInt(0x100000);
    return String.format("%05X", randomNum).toLowerCase(Locale.ROOT);
  }
}
