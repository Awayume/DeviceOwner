package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class PermissionPolicyChangeService extends Service {

    private Context context;
    private final IBinder iBinder = new PermissionPolicyChangeService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public class binder extends Binder{
        PermissionPolicyChangeService getService(){
            return PermissionPolicyChangeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public void setPermissionPolicy(int policy){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setPermissionPolicy(new ComponentName(context, DeviceOwnerReceiver.class), policy);
    }

    @SuppressLint("NewApi")
    public int getPermissionPolicy(){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        return devicePolicyManager.getPermissionPolicy(new ComponentName(context, DeviceOwnerReceiver.class));
    }
}
