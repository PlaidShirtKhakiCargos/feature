package com.psck.feature.exception;

import lombok.NonNull;

public class FeatureCreationException extends FeatureException {
  public FeatureCreationException(@NonNull String message, Object... messageParameters) {
    super(message, messageParameters);
  }
}
