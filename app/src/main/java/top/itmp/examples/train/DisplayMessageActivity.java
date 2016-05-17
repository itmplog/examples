package top.itmp.examples.train;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 16/5/17.
 */
public class DisplayMessageActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_message);

        TextView textView = new TextView(this);
        textView.setTextSize(40f);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(FirstActivity.EXTRA_MESSAGE);

        if(msg != null){
            textView.setText(msg);
            textView.setTextColor(ContextCompat.getColor(this ,R.color.accent));
        }
        setContentView(textView);
    }
}
