package leavesc.hello.binder_pool_client2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import leavesc.hello.binder_pool_server.IBinderPool;
import leavesc.hello.binder_pool_server.ICompute;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:58
 * 描述：
 */
public class MainActivity extends AppCompatActivity {

    private ICompute compute;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                IBinderPool binderPool = IBinderPool.Stub.asInterface(service);
                compute = ICompute.Stub.asInterface(binderPool.queryBinder(200));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            compute = null;
            bindService();
        }
    };

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
        findViewById(R.id.btn_subtraction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compute != null) {
                    try {
                        Log.e(TAG, "4-2 减法：" + compute.subtraction(4, 2));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compute != null) {
            unbindService(serviceConnection);
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("leavesc.hello.binder_pool_server", "leavesc.hello.binder_pool_server.BinderPoolService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

}
