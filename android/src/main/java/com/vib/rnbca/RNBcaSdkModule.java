
package com.vib.rnbca;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNBcaSdkModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNBcaSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNBcaSdk";
  }
}