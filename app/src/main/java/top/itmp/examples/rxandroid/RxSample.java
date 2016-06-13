package top.itmp.examples.rxandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.observers.TestObserver;
import top.itmp.examples.R;

/**
 * Created by hz on 16/6/13.
 */
public class RxSample extends AppCompatActivity{

    private ScrollView scrollView;
    private TextView textView;
    private Button click;
    private Button click1;

    Observable<List<String>> listObservable = Observable.just(getColorList());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxsample);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView = (TextView) findViewById(R.id.text);
        click = (Button) findViewById(R.id.button);
        click1 = (Button) findViewById(R.id.button1);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listObservable.subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        if(strings.size() > 0){
                            for (int i = 0; i < strings.size(); i++) {
                                textView.append(strings.get(i) + "\n");
                            }
                            textView.append("\n");
                        }else {
                            Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        });

    }

    public List<String> getColorList() {
        List<String> colorList = new ArrayList<>();
        colorList.add("red");
        colorList.add("blue");
        colorList.add("green");
        return colorList;
    }
}
