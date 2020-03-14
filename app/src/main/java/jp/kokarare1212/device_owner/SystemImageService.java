package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executor;

public class SystemImageService extends Service {
    private Context context;
    private IBinder iBinder = new SystemImageService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        SystemImageService getService(){
            return SystemImageService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public void installSystemImage(Uri updatePath){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.installSystemUpdate(new ComponentName(context, DeviceOwnerReceiver.class), updatePath, new Executor() {
            @Override
            public void execute(Runnable command) {

            }
        }, new DevicePolicyManager.InstallSystemUpdateCallback() {
            @Override
            public void onInstallUpdateError(int errorCode, @NonNull String errorMessage) {
                super.onInstallUpdateError(errorCode, errorMessage);
            }
        });
    }
}
