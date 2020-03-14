package jp.kokarare1212.device_owner;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class PackageUninstallBlockService extends Service {

    private Context context;
    private final IBinder iBinder = new PackageUninstallBlockService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder{
        PackageUninstallBlockService getService(){
            return PackageUninstallBlockService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void setUninstallBlocked(String packageName, boolean block){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        devicePolicyManager.setUninstallBlocked(new ComponentName(context, DeviceOwnerReceiver.class), packageName, block);
    }

    public boolean isUninstallBlocked(String packageName){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        return devicePolicyManager.isUninstallBlocked(new ComponentName(context, DeviceOwnerReceiver.class), packageName);
    }
}
