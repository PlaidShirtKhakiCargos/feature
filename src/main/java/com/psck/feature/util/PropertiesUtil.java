package com.psck.feature.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {
  private static final List<String> VALID_BOOLEAN_PROPERTIES = Arrays.asList("1", "true", "yes", "on");

  public static Properties load(String fileName) throws IOException {
    return PropertiesLoaderUtils.loadAllProperties(fileName);
  }

  public static boolean isTrue(Object value) {
    return value != null && VALID_BOOLEAN_PROPERTIES.contains(((String)value).toLowerCase());
  }
}
