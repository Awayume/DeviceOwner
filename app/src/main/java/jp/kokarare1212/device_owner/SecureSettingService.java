package jp.kokarare1212.device_owner;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SecureSettingService extends Service {

    private Context context;
    private IBinder iBinder = new SecureSettingService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder{
        SecureSettingService getService(){
            return SecureSettingService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void setSecureSetting(String settingName, String settingValue){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setSecureSetting(new ComponentName(context, DeviceOwnerReceiver.class), settingName, settingValue);
    }
}
