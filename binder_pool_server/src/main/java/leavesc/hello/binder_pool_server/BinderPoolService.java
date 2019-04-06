package leavesc.hello.binder_pool_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:58
 * 描述：
 */
public class BinderPoolService extends Service {

    private class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderId) {
            switch (binderId) {
                case 100: {
                    return new leavesc.hello.binder_pool_server.IOperationImpl();
                }
                case 200: {
                    return new leavesc.hello.binder_pool_server.IComputeImpl();
                }
            }
            return null;
        }

    }

    private Binder binderPool;

    public BinderPoolService() {
        binderPool = new BinderPoolImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binderPool;
    }

}
