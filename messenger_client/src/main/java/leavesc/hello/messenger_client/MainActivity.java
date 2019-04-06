package leavesc.hello.messenger_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:46
 * 描述：
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int CODE_MESSAGE = 1;

    private Messenger messenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    private EditText et_message;

    private Messenger replyMessenger;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_MESSAGE: {
                    Log.e(TAG, "客户端收到了服务端回复的消息：" + msg.arg1);
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(leavesc.hello.messenger_client.R.layout.activity_main);
        bindService();
        initView();
        replyMessenger = new Messenger(new MessengerHandler());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("leavesc.hello.messenger_server", "leavesc.hello.messenger_server.MessengerService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        et_message = findViewById(leavesc.hello.messenger_client.R.id.et_message);
        Button btn_sendMessage = findViewById(leavesc.hello.messenger_client.R.id.btn_sendMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messenger == null) {
                    return;
                }
                String content = et_message.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                int arg1 = Integer.valueOf(content);
                Intent intent = new Intent("Action");
                Message message = new Message();
                message.what = CODE_MESSAGE;
                message.arg1 = arg1;
                message.obj = intent;
                //双向通信时需要加上这一句
                message.replyTo = replyMessenger;
                try {
                    messenger.send(message);
                    Log.e(TAG, "消息发送成功");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
