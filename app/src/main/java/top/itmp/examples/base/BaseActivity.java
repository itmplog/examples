package top.itmp.examples.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by hz on 2016/4/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
