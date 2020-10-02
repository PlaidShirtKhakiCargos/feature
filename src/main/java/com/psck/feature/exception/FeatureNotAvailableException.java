package com.psck.feature.exception;

import lombok.NonNull;

public class FeatureNotAvailableException extends FeatureException {
  public FeatureNotAvailableException(@NonNull String message, Object... messageParameters) {
    super(message, messageParameters);
  }
}
