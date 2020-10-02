package com.psck.feature.api;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesFeatureResolver implements com.psck.feature.api.FeatureResolver {
  private static final String       DEFAULT_PROPERTIES_FILE  = "classpath:features.properties";
  private static final List<String> VALID_BOOLEAN_PROPERTIES = Arrays.asList("1", "true", "yes", "on");

  private Properties properties;
  private String     propertiesPrefix;
  private boolean    isNotFoundFeatureEnabled;

  public PropertiesFeatureResolver() throws IOException {
    this(DEFAULT_PROPERTIES_FILE, null, false);
  }

  public PropertiesFeatureResolver(String propertiesFile) throws IOException {
    this(propertiesFile, null, false);
  }

  public PropertiesFeatureResolver(String propertiesFile, boolean isNotFoundFeatureEnabled) throws IOException {
    this(propertiesFile, null, isNotFoundFeatureEnabled);
  }

  public PropertiesFeatureResolver(String propertiesFile, String propertiesPrefix, boolean isNotFoundFeatureEnabled) throws IOException {
    this.propertiesPrefix = propertiesPrefix;
    this.properties = PropertiesLoaderUtils.loadAllProperties(propertiesFile);
    this.isNotFoundFeatureEnabled = isNotFoundFeatureEnabled;
  }

  @Override
  public boolean isAllowed(String featureName) {
    String propertyName  = StringUtils.isEmpty(propertiesPrefix) ? featureName : MessageFormat.format("{}.{}", propertiesPrefix, featureName);
    Object propertyValue = properties.getOrDefault(propertyName, isNotFoundFeatureEnabled);

    return VALID_BOOLEAN_PROPERTIES.contains(((String)propertyValue).toLowerCase());
  }
}
