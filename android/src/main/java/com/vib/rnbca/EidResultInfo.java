package com.vib.rnbca;


import android.graphics.Bitmap;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.ByteArrayOutputStream;


interface EidResultInfoInterface {
    void onSuccess (String s);
    void ErrorCallback(String s);
    void ErrorCodeCallback(String s);
}


public class EidResultInfo extends ReactContext  implements EidResultInfoInterface {
    private ReactContext reactContext;
    public EidResultInfo(ReactContext context) {
        super(context);
        reactContext = context;
    }

    private void sendEvent(ReactContext reactContext, String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    @Override
    public void onSuccess(  String s) {
        WritableMap params = Arguments.createMap();
        params.putString("success", "true");
        params.putString("rawData", s);
        sendEvent(reactContext, "EventReadCardSuccess", params);
    }

    @Override
    public void ErrorCallback(String s) {
        WritableMap params = Arguments.createMap();
        params.putString("success", "false");
        params.putString("error", s);
        sendEvent(reactContext, "EventReadCardErrorCallback", params);
    }

    @Override
    public void ErrorCodeCallback(String s) {
        WritableMap params = Arguments.createMap();
        params.putString("success", "false");
        params.putString("error", s);
        sendEvent(reactContext, "EventErrorCodeCallback", params);
    }
}
