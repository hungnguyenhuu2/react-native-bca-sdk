package com.vib.rnbca;

import android.annotation.SuppressLint;
import android.nfc.tech.IsoDep;
import android.os.AsyncTask;
import android.util.Log;

import com.htc.sdk.IDCardReader;
import com.htc.sdk.model.CardResult;
import com.htc.sdk.model.IDCardDetail;
import com.htc.sdk.model.ResultCode;

public class ReadTask extends AsyncTask<Void, String, Exception> {

    private IsoDep isoDep;
    private static final String TAG = "ReadTask_HUNGNH>>>>>>>";
//    private MainActivity.OnCompleteListener onCompleteListener;

    public ReadTask(IsoDep isoDep) {
        this.isoDep = isoDep;
//        this.onCompleteListener = onCompleteListener;
    }

    private IDCardDetail citizenInfo = new IDCardDetail();
    CardResult mCardResult;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("NewApi")
    @Override
    protected Exception doInBackground(Void... params) {
        try {
            publishProgress("Đang giao tiếp NFC");
            IDCardReader rd = new IDCardReader();
            String cccdId = "001094023646";
            mCardResult = rd.readData(isoDep, cccdId, true, true, true);
            CardResult result = mCardResult;
            Log.d(TAG, "result>>>>>>> " + result.getCode());
            if (result != null && (result.getCode() == ResultCode.SUCCESS || result.getCode() == ResultCode.SUCCESS_WITH_WARNING)) {
                citizenInfo = rd.parseCardDetail(result);
            }
            Log.d(TAG, "FullName " + citizenInfo.getFullName());
            Log.d(TAG, "FullName " + citizenInfo.getBirthDate());
//            onCompleteListener.onComplete(citizenInfo);
            Log.d(TAG, "Read data result code: " + result.getCode() +
                    "; Hash Checking: " + result.getHashCheck() +
                    "; Chip Authentication: " + result.getChipCheck() +
                    "; Activate Authentication: " + result.getActiveCheck());
        } catch (Exception e) {
            return e;
        }
        return null;
    }
}