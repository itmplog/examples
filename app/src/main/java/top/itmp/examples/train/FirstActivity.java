package top.itmp.examples.train;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

public class FirstActivity extends BaseActivity {

    public static final String EXTRA_MESSAGE = "top.itmp.train.firstActivity.message";
    private EditText message;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        message = (EditText)findViewById(R.id.edit_message);
        send = (Button)findViewById(R.id.button_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, DisplayMessageActivity.class);
                String msg = message.getText().toString();
                if(msg != null && msg.length() > 0){
                    intent.putExtra(EXTRA_MESSAGE, msg);
                }
                startActivity(intent);
            }
        });

    }
}
