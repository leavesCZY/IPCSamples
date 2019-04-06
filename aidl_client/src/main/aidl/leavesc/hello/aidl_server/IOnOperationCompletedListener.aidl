package leavesc.hello.aidl_server;

import leavesc.hello.aidl_server.Parameter;

interface IOnOperationCompletedListener {

    void onOperationCompleted(in Parameter result);

}
