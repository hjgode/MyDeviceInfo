package com.demos.mydeviceinfo;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.demos.mydeviceinfo.common.Logd;

public class BaseActivity extends AppCompatActivity {
    protected boolean isHasPermission(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean permissionFlag = true;
            for (String singlePermission : permissions) {
                permissionFlag = getApplicationContext().checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED;
                Logd("check "+singlePermission+" => " + (permissionFlag?"granted":"denied"));
            }
            return permissionFlag;
        }
        return true;
    }
    protected void askPermission(@IntRange(from = 0) int requestCode, String... permissions) {
        Logd("askPermission: " + requestCode + permissions.toString());
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

}
