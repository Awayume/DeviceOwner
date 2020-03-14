package jp.kokarare1212.device_owner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class PasswordService extends Service {

    private Context context;
    private IBinder iBinder = new PasswordService.binder();

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        PasswordService getService(){
            return PasswordService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @SuppressLint("NewApi")
    public boolean changePassword(String password, int flug){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return false;
        }
        byte[] token = new byte[32];
        devicePolicyManager.setResetPasswordToken(new ComponentName(context, DeviceOwnerReceiver.class), token);
        if(!devicePolicyManager.isResetPasswordTokenActive(new ComponentName(context, DeviceOwnerReceiver.class))){
            return false;
        }
        return devicePolicyManager.resetPasswordWithToken(new ComponentName(context, DeviceOwnerReceiver.class), password, token, flug);
    }
}
