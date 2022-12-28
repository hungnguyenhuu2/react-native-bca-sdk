
package com.vib.rnbca;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import java.util.ArrayList;
import com.vib.rnbca.ReadTask;
import android.nfc.tech.IsoDep;

public class RNBcaSdkPackage implements ReactPackage {
    private IsoDep isoDep;
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//      return Arrays.<NativeModule>asList(new RNBcaSdkModule(reactContext));
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNBcaSdkModule(reactContext));
        return modules;
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }
}