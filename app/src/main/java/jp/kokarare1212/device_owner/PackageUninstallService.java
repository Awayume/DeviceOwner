package jp.kokarare1212.device_owner;

import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Binder;
import android.os.IBinder;

public class PackageUninstallService extends Service {

    private Context context;
    private final IBinder iBinder = new PackageUninstallService.binder();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        PackageUninstallService getService(){
            return PackageUninstallService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void UninstallPackage(String packageName, PendingIntent pendingIntent){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return;
        }
        PackageInstaller packageInstaller = getPackageManager().getPackageInstaller();
        packageInstaller.uninstall(packageName, pendingIntent.getIntentSender());
    }
}
