package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class RebootService extends Service {

    private Context context;
    private IBinder iBinder = new RebootService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        RebootService getService(){
            return RebootService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public void reboot(){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        devicePolicyManager.reboot(new ComponentName(context, DeviceOwnerReceiver.class));
    }
}
