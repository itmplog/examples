package top.itmp.examples.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import top.itmp.examples.R;

/**
 * Created by hz on 2016/4/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}
