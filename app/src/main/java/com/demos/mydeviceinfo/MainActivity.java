package com.demos.mydeviceinfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.demos.mydeviceinfo.common.Logd;

public class MainActivity extends BaseActivity {
    Context context=this;
    os_sdk osSdk=null;
    TextView txtLog;
    Button btnRefresh;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_WIFI = 98;

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    };
    private static final int COARSE_LOCATEION_REQUEST_CODE = 6116;
    private static final int WIFI_STATE_REQUEST_CODE = 6117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLog=findViewById(R.id.txtLog);
        txtLog.setMovementMethod(new ScrollingMovementMethod());
        btnRefresh=findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRefresh();
            }
        });
        osSdk=new os_sdk(context);

        if(!isHasPermission(permissions)){
            askPermission(COARSE_LOCATEION_REQUEST_CODE, permissions);
            askPermission(WIFI_STATE_REQUEST_CODE, permissions);
        }
    }

    @Override
    protected void onDestroy(){
        osSdk.close();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Logd("onRequestPermissionsResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)  == PackageManager.PERMISSION_GRANTED) {
                        Logd("CoarseLocation granted");
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Logd("CoarseLocation denied");
                }
                return;
            }

        }
    }
    void doRefresh(){
        txtLog.setText("");
        String sOut="";

        addLog(DeviceInfos.getVersion(context));

        addLog(DeviceInfos.getVersion(context, "com.honeywell.demos.scandemo"));

        sOut=osSdk.getSerial();
        addLog("Serial number (OS SDK): "+sOut);

        sOut=getWifiMac();
        addLog("Wifi Mac="+sOut);


        sOut = osSdk.getWifiMac();
        addLog("Wifi Mac (OS SDK)="+sOut);

        sOut = DeviceInfos.getIP(context);
        addLog("IP address: "+sOut);

        addLog("============== SystemPropertyAccess ==============");
        addLog(sysProps());
    }

    String sysProps(){
        //JUST some code showing how to get Build version etc.
        //Log.i(TAG, SystemPropertyAccess.getCommonESversion(getApplicationContext()));
        StringBuilder sV=new StringBuilder().append("\n================================================\nService Pack: "+SystemPropertyAccess.getServicePack()+"\n");
        sV.append(sV);
        sV.append("HSMVersionInfo: \n"+ SystemPropertyAccess.getHSMVersionInfo()+"\n");

        sV.append("Build Number: " + android.os.Build.DISPLAY+"\n");
        sV.append("\nPkgInfo: \n"+SystemPropertyAccess.getPkgInfo(getApplicationContext())+"\n");

        sV.append ("\n================================================\n");

        sV.append("SerialNumber: "+SystemPropertyAccess.getSerialNumber()+"\n");
        sV.append("SerialNumber2: "+SystemPropertyAccess.getSerialNumber2()+"\n");
        sV.append ("\n================================================\n");
        return sV.toString();
    }

    void addLog(String msg){
        Logd(msg);
        txtLog.append(msg+"\n");
    }
    String getWifiMac(){
        return DeviceInfos.getMacAddr();
//        return DeviceInfos.getWifiMacAddress(context);
    }
}
