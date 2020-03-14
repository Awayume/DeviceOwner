package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class StatusBarService extends Service {

    private Context context;
    private IBinder iBinder = new StatusBarService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        StatusBarService getService(){
            return StatusBarService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public void setDisableStatusBar(boolean flag){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setStatusBarDisabled(new ComponentName(context, DeviceOwnerReceiver.class), flag);
    }
}
