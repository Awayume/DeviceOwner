package jp.kokarare1212.device_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
        final DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if(devicePolicyManager.isDeviceOwnerApp(getPackageName())){
            ((TextView)findViewById(R.id.enabled)).setText("有効");
            ((Button)findViewById(R.id.disable)).setVisibility(View.VISIBLE);
        } else{
            ((TextView)findViewById(R.id.enabled)).setText("無効");
        }
        ((Button)findViewById(R.id.disable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!devicePolicyManager.isDeviceOwnerApp(getPackageName())){
                    return;
                }
                devicePolicyManager.clearDeviceOwnerApp(getPackageName());
            }
        });
    }
}
