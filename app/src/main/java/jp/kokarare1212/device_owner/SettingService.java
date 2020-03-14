package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SettingService extends Service {

    private Context context;
    private IBinder iBinder = new SettingService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        SettingService getService(){
            return SettingService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void setGlobalSetting(String settingName, String settingValue){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setGlobalSetting(new ComponentName(context, DeviceOwnerReceiver.class), settingName, settingValue);
    }
    @SuppressLint("NewApi")
    public void setSystemSetting(String settingName, String settingValue){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setSystemSetting(new ComponentName(context, DeviceOwnerReceiver.class), settingName, settingValue);
    }
}
