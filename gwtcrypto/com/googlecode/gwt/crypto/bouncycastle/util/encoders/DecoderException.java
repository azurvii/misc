package com.googlecode.gwt.crypto.bouncycastle.util.encoders;

/**
 * Exception thrown if an attempt is made to decode invalid data, or some other failure occurs.
 */
@SuppressWarnings("serial")
public class DecoderException extends IllegalStateException {
  private Throwable cause;

  DecoderException(String msg, Throwable cause) {
    super(msg);

    this.cause = cause;
  }

  @Override
  public Throwable getCause() {
    return cause;
  }
}
