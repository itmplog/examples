package top.itmp.examples.fabric;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;
import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 2016/6/15.
 */
public class PubNubActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button1)
    Button button1;
    private Unbinder bind;
    private Pubnub pubnub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Fabric.with(this);
        setContentView(R.layout.activity_fabric_pubnub);
        bind = ButterKnife.bind(this);

        button.setOnClickListener(this);
        button1.setOnClickListener(this);

        init();

    }

    private void init() {
        //Subscribe to Channel(s)
        //To receive messages from other user in real-time is you need to subscribe to desired channels first..
        pubnub = new Pubnub(getString(R.string.com_pubnub_publishKey), getString(R.string.com_pubnub_subscribeKey));

        try {
            pubnub.subscribe("demo_channel", new Callback() {

                        @Override
                        public void connectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                    + " : " + error.toString());
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        //To receive presence events from other user in real-time is you need to subscribe to special presence channels.
        //Pubnub pubnub = new Pubnub(getString(R.string.com_pubnub_publishKey), getString(R.string.com_pubnub_subscribeKey));

        try {

            pubnub.presence("demo_channel", new Callback() {

                        @Override
                        public void connectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                    + " : " + error.toString());
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                //Publish Message
                //Sending any JSON compatible object to channel is really easy. Here is an an example to get you started.
                //Pubnub pubnub = new Pubnub(getString(R.string.com_pubnub_publishKey), getString(R.string.com_pubnub_subscribeKey));

                pubnub.publish("demo_channel", "demo_message", new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        System.out.println(message);
                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        System.out.println(error);
                    }
                });
                break;
            case R.id.button1:

              /*  Channel History
                Fetch channel messages.
                Initialize and request last 100 messages which has been sent to the channels*/
                //Pubnub pubnub = new Pubnub(getString(R.string.com_pubnub_publishKey), getString(R.string.com_pubnub_subscribeKey));

                pubnub.history("demo", 10, new Callback(){
                    @Override
                    public void successCallback(String channel, Object message) {
                        System.out.println(message);
                    }
                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        System.out.println(error);
                    }
                });

                break;
        }
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
