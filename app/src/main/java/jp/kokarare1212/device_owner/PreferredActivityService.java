package jp.kokarare1212.device_owner;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class PreferredActivityService extends Service {

    private Context context;
    private IBinder iBinder = new PreferredActivityService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        PreferredActivityService getService(){
            return PreferredActivityService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void clearPreferredActivity(String packageName){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        devicePolicyManager.clearPackagePersistentPreferredActivities(new ComponentName(context, DeviceOwnerReceiver.class), packageName);
    }

    public void addPreferredActivity(ComponentName componentName, IntentFilter intentFilter){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.addPersistentPreferredActivity(new ComponentName(context, DeviceOwnerReceiver.class), intentFilter, componentName);
    }
}
