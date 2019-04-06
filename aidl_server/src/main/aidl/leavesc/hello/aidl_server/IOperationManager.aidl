package leavesc.hello.aidl_server;

import leavesc.hello.aidl_server.Parameter;
import leavesc.hello.aidl_server.IOnOperationCompletedListener;

interface IOperationManager {

   void operation(in Parameter parameter1 , in Parameter parameter2);

   void registerListener(in IOnOperationCompletedListener listener);

   void unregisterListener(in IOnOperationCompletedListener listener);

}
