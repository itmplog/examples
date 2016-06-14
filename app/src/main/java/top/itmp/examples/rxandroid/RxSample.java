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

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.itmp.examples.R;

/**
 * Created by hz on 16/6/13.
 */
public class RxSample extends AppCompatActivity{

    private ScrollView scrollView;
    private TextView textView;
    private Button click;
    private Button click1;

    private Observable<List<String>> listObservable = Observable.just(getColorList());

    private Observable<List<String>> listCallObservable = Observable.fromCallable(new Callable<List<String>>() {
        @Override
        public List<String> call() throws Exception {
            return getColorList();
        }
    });

    private Single<List<String>> single = Single.fromCallable(new Callable<List<String>>() {
        @Override
        public List<String> call() throws Exception {
            return null;
        }
    });
    private Subscription subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxsample);

        FirebaseCrash.log("RxSample opened!");

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView = (TextView) findViewById(R.id.text);
        click = (Button) findViewById(R.id.button);
        click1 = (Button) findViewById(R.id.button1);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                textView.setTextColor(Color.CYAN);

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


                subscribe = listCallObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<String> strings) {

                                //int oldColor = textView.getCurrentTextColor();
                                if (strings.size() > 0) {
                                    for (int i = 0; i < strings.size(); i++) {
                                        textView.append(strings.get(i) + "\n");
                                    }
                                    textView.append("\n");
                                    //textView.setTextColor(oldColor);
                                } else {
                                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        });

        single.subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> value) {
                        Toast.makeText(getApplicationContext(), "success",Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT)
                                .show();
                    }
                });


        Observable.just("Hello, world!")
                //.subscribe(s -> System.out.println(s));
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "llsf";
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })

                /*.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(Integer.valueOf(integer));
                    }
                });*/

                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        //return Integer.toString(integer);
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText("");
                textView.setTextColor(Color.MAGENTA);
                //Observable.just("aa", "bb", "cc")
                List<String> lists = new ArrayList<String>(Arrays.asList("aa, bb, cc, dd".split(", ")));

                Observable.from(lists)
                        .filter(new Func1<String, Boolean>() {
                            @Override
                            public Boolean call(String s) {
                                return s != null;
                            }
                        })
                        .subscribe(s -> {
                               textView.append(s);
                        });
            }
        });

    }

    @Override
    protected void onDestroy() {

        if(subscribe != null && subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }

    public List<String> getColorList() {
        List<String> colorList = new ArrayList<>();
        colorList.add("red");
        colorList.add("blue");
        colorList.add("green");
        return colorList;
    }
}
