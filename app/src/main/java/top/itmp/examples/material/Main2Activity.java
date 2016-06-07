package top.itmp.examples.material;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import top.itmp.examples.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter customAdapter = new CustomAdapter();
        customAdapter.lists.add(new Pair<>("Java", Color.BLUE));
        customAdapter.lists.add(new Pair<>("C++", Color.MAGENTA));
        customAdapter.lists.add(new Pair<>("Swift", Color.CYAN));
        customAdapter.lists.add(new Pair<>("Nodejs", Color.DKGRAY));
        customAdapter.lists.add(new Pair<>("Objective-C", Color.YELLOW));
        customAdapter.lists.add(new Pair<>("Java", Color.BLUE));
        customAdapter.lists.add(new Pair<>("C++", Color.MAGENTA));
        customAdapter.lists.add(new Pair<>("Swift", Color.CYAN));
        customAdapter.lists.add(new Pair<>("Nodejs", Color.DKGRAY));
        customAdapter.lists.add(new Pair<>("Objective-C", Color.YELLOW));
        customAdapter.lists.add(new Pair<>("Java", Color.BLUE));
        customAdapter.lists.add(new Pair<>("C++", Color.MAGENTA));
        customAdapter.lists.add(new Pair<>("Swift", Color.CYAN));
        customAdapter.lists.add(new Pair<>("Nodejs", Color.DKGRAY));
        customAdapter.lists.add(new Pair<>("Objective-C", Color.YELLOW));

        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
        private ArrayList<Pair<String, Integer>> lists;

        public CustomAdapter() {
            super();
            lists = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            int padding = (int)(8 * getResources().getDisplayMetrics().density + 0.5);
            textView.setPadding(padding, padding, padding, padding);
            textView.setGravity(Gravity.CENTER);
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText(lists.get(position).first);
            holder.itemView.setBackgroundColor(lists.get(position).second);
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
