package com.demos.mydeviceinfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.honeywell.osservice.sdk.*;
import com.honeywell.osservice.data.OSConstant;

public class os_sdk {

    private DeviceManager deviceManager=null;
    public os_sdk(Context context){
        deviceManager = DeviceManager.getInstance(context);

    }

    public void close(){
        if(deviceManager!=null && deviceManager.isReady())
            deviceManager.close();
    }

    public String getSerial(){
        String sOut="not ready";
        try {
            if(!deviceManager.isReady())
                return "not ready";
            String s = deviceManager.getSerialNumber();
            if(s!=null)
                sOut=s;
        }catch (HonOSException ex){
            Log.d(common.TAG, "Exception in getWifiMac"+ ex.getMessage());
        }
        return sOut;
    }

    public String getWifiMac(){
        String sMac="error in getWifiMac";
        try {
            if(!deviceManager.isReady())
                return "not ready";
            Bundle bundle = deviceManager.getWifiMacAddress(OSConstant.KEY_RESULT_WIFI_MAC_ADDRESS);
            String s=bundle.getString(OSConstant.KEY_RESULT_WIFI_MAC_ADDRESS);
            if(s!=null)
                sMac=s;
        }catch (HonOSException ex){
            Log.d(common.TAG, "Exception in getWifiMac"+ ex.getMessage());
        }
        return sMac;
    }
}
