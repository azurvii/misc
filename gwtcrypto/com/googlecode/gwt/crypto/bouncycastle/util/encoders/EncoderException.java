package com.googlecode.gwt.crypto.bouncycastle.util.encoders;

/**
 * Exception thrown if an attempt is made to encode invalid data, or some other failure occurs.
 */
@SuppressWarnings("serial")
public class EncoderException extends IllegalStateException {
  private Throwable cause;

  EncoderException(String msg, Throwable cause) {
    super(msg);

    this.cause = cause;
  }

  @Override
  public Throwable getCause() {
    return cause;
  }
}
