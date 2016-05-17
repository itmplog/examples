package top.itmp.examples.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import top.itmp.examples.R;

/**
 * Created by hz on 2016/4/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
