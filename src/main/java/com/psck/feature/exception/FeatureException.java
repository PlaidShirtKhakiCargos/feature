package com.psck.feature.exception;

import lombok.NonNull;

import java.text.MessageFormat;

class FeatureException extends RuntimeException {
  FeatureException(@NonNull String message, Object... messageParameters) {
    super((messageParameters == null) ? message : MessageFormat.format(message, messageParameters), null);
  }
}
