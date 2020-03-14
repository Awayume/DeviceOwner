package jp.kokarare1212.device_owner;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PackageInstallService extends Service {

    private Context context;
    private final IBinder iBinder = new binder();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public class binder extends Binder {
        PackageInstallService getService(){
            return PackageInstallService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public boolean InstallPackage(String apkPath, PendingIntent pendingIntent){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            return false;
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        boolean sccess;
        try {
            int sessionId = createSession(packageInstaller);
            if (sessionId < 0) {
                return false;
            }
            writeSession(packageInstaller, sessionId, apkPath);
            sccess = commitSession(packageInstaller, sessionId, pendingIntent);
        } catch (Exception e){
            e.printStackTrace();
            sccess = false;
        }
        return sccess;
    }

    private int createSession(PackageInstaller packageInstaller) throws IOException {
        final PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setInstallLocation(PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY);
        return packageInstaller.createSession(params);
    }

    private int writeSession(PackageInstaller packageInstaller, final int sessionId, String apkPath) throws IOException {
        long sizeBytes = -1;
        final String splitName = "INSTALL";

        final File file = new File(apkPath);
        if (file.isFile()) {
            sizeBytes = file.length();
        }
        PackageInstaller.Session session = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            session = packageInstaller.openSession(sessionId);
            in = new FileInputStream(apkPath);
            out = session.openWrite(splitName, 0, sizeBytes);
            int total = 0;
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1) {
                total += c;
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            return 0;
        } finally {

            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (session != null)
                session.close();
        }
    }

    private boolean commitSession(PackageInstaller packageInstaller, final int sessionId, PendingIntent pendingIntent) throws IOException {
        PackageInstaller.Session session = null;
        boolean sccess = true;
        try {
            session = packageInstaller.openSession(sessionId);
            session.commit(pendingIntent.getIntentSender());
        } catch (Exception e) {
            e.printStackTrace();
            sccess = false;
        } finally {
            session.close();
        }
        return sccess;
    }
}
