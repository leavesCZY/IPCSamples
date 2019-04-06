package leavesc.hello.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:45
 * 描述：
 */
public class AIDLService extends Service {

    private static final String TAG = "AIDLService";

    //声明
    private RemoteCallbackList<IOnOperationCompletedListener> callbackList;

    private IOperationManager.Stub stub = new IOperationManager.Stub() {
        @Override
        public void operation(Parameter parameter1, Parameter parameter2) throws RemoteException {
            try {
                Log.e(TAG, "operation 被调用，延时5秒，模拟耗时计算");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int param1 = parameter1.getParam();
            int param2 = parameter2.getParam();
            Parameter result = new Parameter(param1 * param2);
            //在操作 RemoteCallbackList 前，必须先调用其 beginBroadcast 方法
            //此外，beginBroadcast 必须和 finishBroadcast配套使用
            int count = callbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IOnOperationCompletedListener listener = callbackList.getBroadcastItem(i);
                if (listener != null) {
                    listener.onOperationCompleted(result);
                }
            }
            callbackList.finishBroadcast();
            Log.e(TAG, "计算结束");
        }

        @Override
        public void registerListener(IOnOperationCompletedListener listener) throws RemoteException {
            callbackList.register(listener);
            Log.e(TAG, "registerListener 注册回调成功");
        }

        @Override
        public void unregisterListener(IOnOperationCompletedListener listener) throws RemoteException {
            callbackList.unregister(listener);
            Log.e(TAG, "unregisterListener 解除注册回调成功");
        }
    };

    public AIDLService() {
        callbackList = new RemoteCallbackList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

}
