package com.demos.mydeviceinfo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;
import static com.demos.mydeviceinfo.common.Logd;

public class DeviceInfos {

    public static String getWifiMacAddress(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        Log.d("DeviceInfos", "Wifi Mac="+macAddress);
        return macAddress;
    }

    public static String getIP(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ipAddress;
    }

    public static String getVersion(Context context){
        String versionName = "";
        int versionCode = -1;
        String sOut="no version info";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
            sOut="Version Name : "+versionName + "\n Version Code : "+versionCode;
            Logd(sOut );
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Logd("PackageManager Catch : "+e.toString());
        }
        return sOut;
    }

    public static String getVersion(Context context, String sPackage){
        String versionName = "";
        int versionCode = -1;
        String sOut="no version info";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(sPackage, 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
            sOut=sPackage + ": Version Name : "+versionName + "\n Version Code : "+versionCode;
            Logd(sOut );
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Logd("PackageManager Catch : "+e.toString());
        }
        return sOut;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif: all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b: macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {}
        return "02:00:00:00:00:00";
    }
}
