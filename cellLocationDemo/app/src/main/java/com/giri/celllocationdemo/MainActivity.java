package com.giri.celllocationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "GSMCellLocationActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取基站信息
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                // 返回值MCC + MNC
                String operator = mTelephonyManager.getNetworkOperator();
                int mcc = Integer.parseInt(operator.substring(0, 3));
                int mnc = Integer.parseInt(operator.substring(3));

                // 判断运营商
                if (mnc == 00 || mnc == 01) {

                    /** 中国移动和中国联通获取LAC、CID的方式 */
                    // 权限检查
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                    int lac = location.getLac();
                    int cellId = location.getCid();

                    Toast.makeText(MainActivity.this, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId,Toast.LENGTH_LONG).show();
                } else {

                    /** 中国电信获取LAC、CID的方式 */

                    //权限校验
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    // 获取
                    CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                    int lac = location1.getNetworkId();
                    int cellId = location1.getBaseStationId();
                    Toast.makeText(MainActivity.this, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId,Toast.LENGTH_LONG).show();
                }


            }
        });

    }


}
