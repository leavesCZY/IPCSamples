package leavesc.hello.binder_pool_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:58
 * 描述：
 */
public class IComputeImpl extends ICompute.Stub {

    @Override
    public int subtraction(int parameter1, int parameter2) throws RemoteException {
        Log.e("IComputeImpl", "subtraction 方法被调用");
        return parameter1 - parameter2;
    }

}
