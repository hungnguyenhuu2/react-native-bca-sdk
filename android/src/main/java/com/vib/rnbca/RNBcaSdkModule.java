
package com.vib.rnbca;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.module.annotations.ReactModule;
import androidx.annotation.NonNull;

import android.util.Log;
import android.content.Intent;
import android.app.Activity;

import com.fis.ekyc.nfc.build_in.model.ResultCode;
import com.fis.nfc.sdk.nfc.stepNfc.NFCStepActivity;
import com.fis.nfc.sdk.nfc.util.CustomNfcSdk;
import com.fis.nfc.sdk.nfc.util.CustomSdk;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import com.facebook.react.bridge.Callback;

@ReactModule(name = RNBcaSdkModule.NAME)
public class RNBcaSdkModule extends ReactContextBaseJavaModule {
  public static final String NAME = "RNBcaSdk";
  private final ReactApplicationContext reactContext;
    private String TAG = "HUNGNH123";
    public String value = "";
    public   EidResultInfo eidResultInfo;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    Callback callback;
  public RNBcaSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
       eidResultInfo = new EidResultInfo(reactContext);
//      eidReadCard = new ReadCard(new EidResultInfo(reactContext));
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

    @ReactMethod
    public void init(String cccdId) {
        try {

            CustomSdk.Companion.setBaseUrl("https://apig.idcheck.xplat.online/");
            CustomSdk.Companion.setIdCard(cccdId);
            CustomSdk.Companion.setAccessToken("eyJ4NXQiOiJPR1ZqTVRNME0yTTFOVFZqTkRNME5EWm1OV0ZsTURSbE1qVTFOVEl4T1dZME1HRTJNMlU1WWciLCJraWQiOiJOekZtTmpFek5XSTNNelE0WWpGaU9HSTVZall5TTJGaVptTXlPREEyTVdaaE5UaG1aakF4TldJNE5ERTJZakl3TnpjMllqWmlZakpsTVRkaU16VTVaUV9SUzI1NiIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJ2aWJfcG9jIiwiYXV0IjoiQVBQTElDQVRJT05fVVNFUiIsImF1ZCI6IndiSFM1WE5vN1dQOWFBbG1fX1N1emNUOFIxRWEiLCJuYmYiOjE2NzI3MTI0NDQsImF6cCI6IndiSFM1WE5vN1dQOWFBbG1fX1N1emNUOFIxRWEiLCJzY29wZSI6ImNoZWNrX25mY19iY2EgY29udGVudF9tYXRjaGluZ19vY3JfbmZjIGRvY19uZmMgaGFzaF9jaGVja19uZmMgb3BlbmlkIHNlc3Npb25fMjMwMTAzMDIyMDQ0IiwiaXNzIjoiaHR0cHM6XC9cL2lkY2hlY2suZWlkLnhwbGF0Lm9ubGluZTo0NDNcL29hdXRoMlwvdG9rZW4iLCJncm91cHMiOlsidXNlci1jaGVjay1uZmMtYmNhIiwidXNlci1uZmMiLCJJbnRlcm5hbFwvZXZlcnlvbmUiLCJ1c2VyLWhhc2gtY2hlY2stbmZjIl0sImV4cCI6MTY3MjczMDQ0NCwiaWF0IjoxNjcyNzEyNDQ0LCJqdGkiOiIyZjk5NzQ4MC1jZTFmLTQyZGEtYWM2MS1kMzZiYjBiMzc0ZjAifQ.ISjIosIMehirLgxZcP-r4fZgai7XtVyL-Wr0rhsZE3Lgo-oqYPNvreVrM2pDGgk5ISWC1dRHk0igLQrpRTRI5UrcZXtWfSSetgQIT0GtwAb2Rul20rL1Gity_xrRsoPvizJiCGKXkfnn1KJ9dUoRSDNnh32LIdpplpjkNiVBxLysIg8VJzarS5tWqXUrWKnxx_SJjYfwTaYe_XAPsCQ6D_CtumCneNXcL6vQkPtZhNeLnildv7HCZDGqSd1tnJjOgX0Z3GXFd-cgFIdGJxO2zD8_BuAc-87YPOpH93-Woyz_fWvcx--fONJU7sMOs912xB2SxTsmab_7G9HPyWq6wg");
            CustomSdk.Companion.setCkeckBoCA(true);
            Log.d(TAG, "CustomSdk>>>>>" +CustomSdk.Companion.getBaseUrl());
            Activity currentActivity = getCurrentActivity();

            Intent intent = new Intent(this.reactContext,  NFCStepActivity.class);
            // return when nfc success
            currentActivity.startActivity(intent);
            currentActivity.finish();
        } catch (Exception e) {
            return;
        }
    }
    @ReactMethod
  public void readCardFIS(String token,String cccdId, Promise promise) {
      try {

          CustomSdk.Companion.setBaseUrl("https://apig.idcheck.xplat.online/");
          CustomSdk.Companion.setIdCard(cccdId);
          CustomSdk.Companion.setAccessToken(token);
          CustomSdk.Companion.setCkeckBoCA(true);
          Activity currentActivity = getCurrentActivity();

          Intent intent = new Intent(this.reactContext,  NFCStepActivity.class);
          // return when nfc success

          CustomNfcSdk.Companion.setNfcFinishCallback(new Function1<String, Unit>() {
              @Override
              public Unit invoke(String s) {
                  Log.d(TAG, "ssssss>>>>>" +s);
                  value = s;
                  Log.d(TAG, "value>>>>>" +value);
                  eidResultInfo.onSuccess(s);
                  promise.resolve(value);
                  return null;
              }
          });
          // return when error
          CustomNfcSdk.Companion.setErrorCallback(new Function1<String, Unit>() {
              @Override
              public Unit invoke(String s) {
                  Log.d(TAG, "setErrorCallback>>>>>>>>>>" +s);
                  eidResultInfo.ErrorCallback(s);
                  promise.resolve(null);
                  return null;
              }
          });
          Log.d(TAG, "value>>>>>>>>>>" +value);
          CustomNfcSdk.Companion.setErrorCodeCallback(new Function1<ResultCode, Unit>() {
              @Override
              public Unit invoke(ResultCode resultCode) {
                  Log.d(TAG, "resultCode>>>>>>>>>>" +resultCode);
//                  promise.reject("READ_INFO_ERROR", "setErrorCodeCallback");
                  eidResultInfo.ErrorCodeCallback(resultCode.toString());
                  promise.resolve(null);
                  return null;

              }
          });
          currentActivity.startActivity(intent);
      } catch (Exception e) {
          return;
      }
        promise.resolve(value);

  }

    @ReactMethod
    public void demo(String cccdId,String abc, Promise promise) {
        Log.d(TAG, "demo>>>>>>>>>>>> ");
         promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
         return;
    }
}
