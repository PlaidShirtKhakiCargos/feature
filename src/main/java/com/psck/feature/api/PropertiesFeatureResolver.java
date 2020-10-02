package com.psck.feature.api;

import com.psck.feature.util.PropertiesUtil;
import lombok.Builder;

import java.io.IOException;


@Builder
@SuppressWarnings("FieldMayBeFinal")
public class PropertiesFeatureResolver extends AbstractPropertiesResolver {
  @Builder.Default
  private String  propertiesFile = "classpath:features.properties";
  private String  propertiesPrefix;
  private boolean isNotFoundFeatureEnabled;

  public PropertiesFeatureResolver(String propertiesFile, String propertiesPrefix, boolean isNotFoundFeatureEnabled) throws IOException {
    super(PropertiesUtil.load(propertiesFile), propertiesPrefix, isNotFoundFeatureEnabled);
  }
}
