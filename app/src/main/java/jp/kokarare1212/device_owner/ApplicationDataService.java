package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.concurrent.Executor;

public class ApplicationDataService extends Service {

    private Context context;
    private IBinder iBinder = new ApplicationDataService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        ApplicationDataService getService(){
            return ApplicationDataService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public void clearApplicationData(String packageName){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.clearApplicationUserData(new ComponentName(context, DeviceOwnerReceiver.class), packageName, new Executor() {
            @Override
            public void execute(Runnable command) {

            }
        }, new DevicePolicyManager.OnClearApplicationUserDataListener() {
            @Override
            public void onApplicationUserDataCleared(String packageName, boolean succeeded) {

            }
        });
    }
}
