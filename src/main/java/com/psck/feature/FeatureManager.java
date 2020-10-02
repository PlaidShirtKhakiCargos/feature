package com.psck.feature;


import com.psck.feature.annotation.Feature;
import com.psck.feature.api.FeatureResolver;
import com.psck.feature.exception.FeatureCreationException;
import com.psck.feature.exception.FeatureNotAvailableException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class FeatureManager {
  private static final Logger                       LOGGER             = LoggerFactory.getLogger(FeatureManager.class);
  private              FeatureResolver              defaultFeatureResolver;
  private              Map<String, FeatureResolver> FeatureResolverMap = new HashMap<>();
  private              boolean                      allowNullFeatures;

  public FeatureManager allowNullFeatures(boolean allowNullFeatures) {
    this.allowNullFeatures = allowNullFeatures;

    return this;
  }

  public FeatureManager addFeatureResolver(String featureName, FeatureResolver checker) {
    LOGGER.trace("Adding feature checker to map: {}", featureName);
    FeatureResolverMap.put(featureName, checker);

    return this;
  }

  public FeatureManager addFeatureResolvers(Map<String, FeatureResolver> FeatureResolvers) {
    LOGGER.trace("Adding named feature checker");
    FeatureResolverMap.putAll(FeatureResolvers);

    return this;
  }

  /**
   * Adding a default checker will only be called if there is no feature specific checker.
   */
  public FeatureManager addDefaultFeatureResolver(FeatureResolver checker) {
    if (defaultFeatureResolver != null) {
      throw new FeatureCreationException("Default Checker already added, can only contain one.");
    }

    LOGGER.trace("Setting default feature checker");
    defaultFeatureResolver = checker;

    return this;
  }

  // todo: put this somewhere else to make sure it it does not get called on its own?
  @Before("execution(public * * (..)) && (@within(com.psck.feature.annotation.Feature) || @annotation(com.psck.feature.annotation.Feature))")
  public void checkFeature(JoinPoint joinPoint) {
    Feature feature     = getAnnotation(joinPoint, Feature.class);
    String  featureName = feature.value();
    String  description = feature.description();

    LOGGER.trace("Running feature validation for feature:[{}]", featureName);
    FeatureResolver resolver = getChecker(featureName);
    if (resolver == null && !allowNullFeatures) {
      throw new FeatureCreationException("No checker found for feature. Supply either a default check or feature specific checker. Feature name found={0}", featureName);
    } else if (resolver != null && !resolver.isAllowed(featureName)) {
      LOGGER.trace("Checking feature, [{}]. {}", featureName, description);
      throw new FeatureNotAvailableException("Feature unavailable; feature name=[{0}].", featureName);
    }
  }

  private FeatureResolver getChecker(String featureName) {
    if (!FeatureResolverMap.containsKey(featureName)) {
      return defaultFeatureResolver;
    }

    return FeatureResolverMap.get(featureName);
  }

  private <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
    MethodSignature signature = (MethodSignature)joinPoint.getSignature();

    LOGGER.trace("Getting annotation: [{}].", annotationClass);
    T methodAnnotation = AnnotationUtils.getAnnotation(signature.getMethod(), annotationClass);

    return (methodAnnotation == null)
        ? AnnotationUtils.getAnnotation(joinPoint.getTarget().getClass(), annotationClass)
        : methodAnnotation;
  }
}

