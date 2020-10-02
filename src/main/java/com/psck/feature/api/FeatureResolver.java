package com.psck.feature.api;

/**
 * Interface to be used if creating a custom Feature Resolver.
 */
public interface FeatureResolver {
  boolean isAllowed(String featureName);
}
