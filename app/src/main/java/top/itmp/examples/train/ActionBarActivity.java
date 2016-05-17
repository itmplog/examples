package top.itmp.examples.train;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 16/5/17.
 */
public class ActionBarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Snackbar.make(getWindow().getDecorView(), getString(R.string.action_search),
                        Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Snackbar.make(getWindow().getDecorView(), getString(R.string.action_settings),
                        Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
