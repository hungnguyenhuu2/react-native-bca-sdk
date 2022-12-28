
package com.vib.rnbca;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import androidx.annotation.NonNull;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

//import android.content.Intent;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.provider.Settings;
//import android.app.AlertDialog;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.nfc.NfcAdapter;
//import android.nfc.Tag;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.htc.sdk.IDCardReader;
//import com.htc.sdk.model.CardResult;
//import com.htc.sdk.model.IDCardDetail;
//import com.htc.sdk.model.ResultCode;
import com.vib.rnbca.ReadTask;


import com.vib.rnbca.TagTechnologyRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;

@ReactModule(name = RNBcaSdkModule.NAME)
public class RNBcaSdkModule extends ReactContextBaseJavaModule {
  public static final String NAME = "RNBcaSdk";
  private final ReactApplicationContext reactContext;
    private String TAG = "HUNGNH123";
    private IsoDep isoDep;
    private TagTechnologyRequest techRequest = null;
private ReadTask readTask = null;
  public RNBcaSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.isoDep = isoDep;
//      performTagOperations();
  }
//
//    private void performTagOperations() {
//        Log.d(TAG, "onNewIntent tag $tag");
//        ReadTask readTask = new ReadTask(this.isoDep);
//        readTask.execute();
//    }

    private void startNfc() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this.reactContext);
        Log.d(TAG, "startNfc: $adapter");
        if (adapter == null) {
            Log.d(TAG, "adapter == null");
//            finish()
//            return
        }
//        performTagOperations();
//        if (!adapter.isEnabled) {
//            Log.d(TAG, "Chưa bật NFC")
//            alertDialog = AlertDialog.Builder(this)
//                    .setTitle("Chưa bật NFC")
//                    .setMessage("Vui lòng bật NFC trong Settings để tiếp tục")
//                    .setCancelable(false)
//                    .setPositiveButton("Cài đặt") { _, _ ->
//                    val intent = Intent(android.provider.Settings.ACTION_NFC_SETTINGS)
//                startActivity(intent)
//            }
//                .setNegativeButton("Bỏ qua") { _, _ ->
//                    finish()
//            }.create()
//            alertDialog?.show()
//        } else {
//            mNfcPendingIntent = PendingIntent.getActivity(
//                    this@MainActivity,
//            0, Intent(this@MainActivity, MainActivity::class.java)
//                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE
//            )
//        }
    }



  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void readCard(String cccdId,ReadableArray techs, Promise promise) {
      try {

//          Tag tag1 = techRequest.getTagHandle();
         Log.d(TAG, "techs>>>>>>>>"+techs);
//          Log.d(TAG, "techs222222>>>>>>>>"+techs.toArrayList());
         techRequest = new TagTechnologyRequest(techs.toArrayList());
         Log.d(TAG, "techRequest>>>>>>>>1111111111"+techRequest.toString());
         Log.d(TAG, "onNewIntent tag $techRequest");
         Tag tag1 = techRequest.getTagHandle();

//          techRequest = new TagTechnologyRequest(isoDep.toArrayList());
//                    Log.d(TAG, "techRequest>>>>>>>>>>>> "+ techRequest);
//          Intent intent = new Intent(this.reactContext, this.getClass());
          Tag tag =
          Log.d(TAG, "readCard>>>>>>>>"+ techs);
//          Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
           readTask = new ReadTask(IsoDep.get(tag1));
          readTask.execute();
          
//          Log.d(TAG, "Chạy đến chưa: ");
//          IDCardReader rd = new IDCardReader();
//          mCardResult = rd.readData(isoDep, "001094023646", true, true, true);
//          CardResult result = mCardResult;
//          if (result != null && (result.getCode() == ResultCode.SUCCESS || result.getCode() == ResultCode.SUCCESS_WITH_WARNING)) {
//              promise.resolve(null);
//              citizenInfo = rd.parseCardDetail(result);
//
//          }
//          Log.d(TAG, "Read data result code: " + result.getCode() + "; Hash Checking: " + result.getHashCheck() +
//                  "; Chip Authentication: " + result.getChipCheck() +
//                  "; Activate Authentication: " + result.getActiveCheck());
      } catch (Exception e) {
//          Log.d(TAG, "Read data result code: " + "éo rõ đúng sai");
          promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
      }
    promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
  }

    @ReactMethod
    public void demo(String cccdId,String abc, Promise promise) {
        Log.d(TAG, "demo>>>>>>>>>>>> ");
         promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
         return ;
    }


}
// package com.vib.rnbca;

// import static android.content.ContentValues.TAG;

// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.bridge.ReactContextBaseJavaModule;
// import com.facebook.react.bridge.ReactMethod;

// import com.facebook.react.bridge.Promise;

// import com.facebook.react.module.annotations.ReactModule;
// import androidx.annotation.NonNull;

// import android.content.Intent;
// import android.nfc.NfcAdapter;
// import android.app.AlertDialog;
// import android.content.DialogInterface;
// import android.nfc.tech.IsoDep;
// import android.provider.Settings;
// import android.util.Log;

// import com.htc.sdk.IDCardReader;
// import com.htc.sdk.model.CardResult;
// import com.htc.sdk.model.IDCardDetail;
// import com.htc.sdk.model.ResultCode;

// @ReactModule(name = RNBcaSdkModule.NAME)
// public class RNBcaSdkModule extends ReactContextBaseJavaModule {
//   public static final String NAME = "RNBcaSdk";
//   private final ReactApplicationContext reactContext;
//   private AlertDialog.Builder alertDialog;
//   private IsoDep isoDep;
//   private IDCardDetail citizenInfo = new IDCardDetail();
//   private CardResult mCardResult;

//     ReadTask readTask;
//   public RNBcaSdkModule(ReactApplicationContext reactContext) {
//     super(reactContext);
//     this.reactContext = reactContext;
//     // eidReadCard = new ReadCard(new EidResultInfo(reactContext));
//   }



//   @Override
//   @NonNull
//   public String getName() {
//     return NAME;
//   }

//   @ReactMethod
//   public void readCard(String cccdId,String abc, Promise promise) {
//       try {
// //          readTask = new ReadTask(this.isoDep);
// //          readTask.execute();
//           // Log.d(TAG, "Chạy đến chưa: ");
//           IDCardReader rd = new IDCardReader();
//           mCardResult = rd.readData(isoDep, "001094023646", true, true, true);
//           CardResult result = mCardResult;
//           promise.resolve(null);
//           if (result != null && (result.getCode() == ResultCode.SUCCESS || result.getCode() == ResultCode.SUCCESS_WITH_WARNING)) {
//               citizenInfo = rd.parseCardDetail(result);
//           }
//           // Log.d(TAG, "Read data result code: " + result.getCode() + "; Hash Checking: " + result.getHashCheck() +
//           //         "; Chip Authentication: " + result.getChipCheck() +
//           //         "; Activate Authentication: " + result.getActiveCheck());
//           return;
//       } catch (Exception e) {
//          Log.d(TAG, "Read data result code: " + e);
//           promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
//       }
//     promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
//       return ;
//   }

//     @ReactMethod
//   public void demo(String cccdId,String abc, Promise promise) {
//     System.out.println("Process>>>>>>>");
//     promise.reject("READ_INFO_ERROR", "DATA_INVAL21212D");
//       return ;
//   }

// }
