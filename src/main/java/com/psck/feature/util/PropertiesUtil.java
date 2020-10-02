package com.psck.feature.util;

import org.omg.SendingContext.RunTime;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {
  private static final List<String> VALID_BOOLEAN_PROPERTIES = Arrays.asList("1", "true", "yes", "on");

  public static Properties load(String fileName) {
    try {
      return PropertiesLoaderUtils.loadAllProperties(fileName);
    } catch (IOException e) {
      throw new RuntimeException(MessageFormat.format("Error loading properties file; filename={0}", fileName), e);
    }
  }

  public static boolean isTrue(Object value) {
    return value != null && VALID_BOOLEAN_PROPERTIES.contains(((String)value).toLowerCase());
  }
}
