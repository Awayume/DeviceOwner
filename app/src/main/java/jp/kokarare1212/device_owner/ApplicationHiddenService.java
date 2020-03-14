package jp.kokarare1212.device_owner;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ApplicationHiddenService extends Service {

    private Context context;
    private IBinder iBinder = new ApplicationHiddenService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        ApplicationHiddenService getService(){
            return ApplicationHiddenService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public boolean setApplicationHidden(String packageName, boolean flug){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return false;
        }
        return devicePolicyManager.setApplicationHidden(new ComponentName(context, DeviceOwnerReceiver.class), packageName, false);
    }
    public boolean getApplicationHidden(String packageName, boolean flug){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return false;
        }
        return devicePolicyManager.isApplicationHidden(new ComponentName(context, DeviceOwnerReceiver.class), packageName);
    }
}
