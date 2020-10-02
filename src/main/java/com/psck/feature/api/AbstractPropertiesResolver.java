package com.psck.feature.api;

import com.psck.feature.util.PropertiesUtil;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Properties;

public abstract class AbstractPropertiesResolver implements FeatureResolver {
  private final Properties properties;
  private final String     propertiesPrefix;
  private final boolean    isNotFoundFeatureEnabled;

  protected AbstractPropertiesResolver(Properties properties, String propertiesPrefix, boolean isNotFoundFeatureEnabled) {
    this.properties = properties;
    this.propertiesPrefix = propertiesPrefix;
    this.isNotFoundFeatureEnabled = isNotFoundFeatureEnabled;
  }

  @Override
  public boolean isAllowed(String featureName) {
    String propertyName  = StringUtils.isEmpty(propertiesPrefix) ? featureName : MessageFormat.format("{}.{}", propertiesPrefix, featureName);
    Object propertyValue = getProperties().getOrDefault(propertyName, isNotFoundFeatureEnabled);

    return PropertiesUtil.isTrue(propertyValue);
  }

  protected Properties getProperties() {
    return properties;
  }
}
