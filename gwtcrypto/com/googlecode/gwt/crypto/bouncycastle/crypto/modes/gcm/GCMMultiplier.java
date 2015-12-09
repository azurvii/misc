package com.googlecode.gwt.crypto.bouncycastle.crypto.modes.gcm;

public interface GCMMultiplier {
  void init(byte[] H);

  void multiplyH(byte[] x);
}
