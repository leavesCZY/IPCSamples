package leavesc.hello.messenger_server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:47
 * 描述：
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static final int CODE_MESSAGE = 1;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_MESSAGE: {
                    Log.e(TAG, "服务端收到了消息：" + msg.arg1 + " " + ((Intent) msg.obj).getAction());
                    //取得客户端的 Messenger 对象
                    Messenger messenger = msg.replyTo;
                    Message message = new Message();
                    message.what = CODE_MESSAGE;
                    message.arg1 = 2 * msg.arg1;
                    try {
                        messenger.send(message);
                        Log.e(TAG, "服务端回复消息成功");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private Messenger messenger;

    public MessengerService() {
        messenger = new Messenger(new MessengerHandler());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

}
