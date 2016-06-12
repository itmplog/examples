package top.itmp.examples.agera;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.agera.Function;
import com.google.android.agera.Merger;
import com.google.android.agera.Observable;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import top.itmp.examples.R;

public class AgeraSampleStart extends AppCompatActivity implements Updatable {

    private TextView textView;
    private Button button;

    private Observable observable = new Observable() {
        @Override
        public void addUpdatable(@NonNull Updatable updatable) {
            updatable.update();
        }

        @Override
        public void removeUpdatable(@NonNull Updatable updatable) {

        }
    };

    private Supplier<Result<String>> supplier = new Supplier<Result<String>>() {

        @NonNull
        @Override
        public Result<String> get() {
            //return "么么哒!!";
            try {
                return Result.success("value");
            }catch (ArithmeticException e){
                return Result.failure(e);
            }
        }
    };

    private Function<String, String> stringFunction = new Function<String, String>() {
        @NonNull
        @Override
        public String apply(@NonNull String input) {
            return "new " + input;
        }
    };

    private Supplier<Result<Integer>> integerSupplier = new Supplier<Result<Integer>>() {
        @NonNull
        @Override
        public Result<Integer> get() {
            try{
                return Result.success(100);
            }catch (ArithmeticException e){
                return Result.failure(e);
            }
        }
    };

    private Merger<Result<String>, Result<Integer>, String> merger = new Merger<Result<String>, Result<Integer>, String>() {
        @NonNull
        @Override
        public String merge(@NonNull Result<String> s, @NonNull Result<Integer> integer) {
            return s + " plus " + String.valueOf(integer);
        }
    };

    private Repository<Result<String>> repositories = Repositories.repositoryWithInitialValue(Result.<String>absent())
            .observe()
            .onUpdatesPerLoop()
            .attemptGetFrom(supplier).orEnd(new Function<Throwable, Result<String>>() {
                @NonNull
                @Override
                public Result<String> apply(@NonNull Throwable input) {
                    return Result.success("heh");
                }
            })
            .transform(stringFunction)
            .thenMergeIn(integerSupplier, new Merger<String, Result<Integer>, Result<String>>() {
                @NonNull
                @Override
                public Result<String> merge(@NonNull String s, @NonNull Result<Integer> integerResult) {
                    return Result.success(s + " plus " + String.valueOf(integerResult));
                }
            })
            //.thenGetFrom(supplier)
            .compile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agera_sample_start);

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //observable.addUpdatable(AgeraSampleStart.this);
                try {
                    repositories.addUpdatable(AgeraSampleStart.this);
                }catch (IllegalStateException e){
                    //e.printStackTrace();
                    repositories.removeUpdatable(AgeraSampleStart.this);
                }
            }
        });

    }

    private Receiver<String> success = new Receiver<String>() {
        @Override
        public void accept(@NonNull String value) {
            textView.setText(value);
            repositories.removeUpdatable(AgeraSampleStart.this);
        }
    };

    private Receiver<Throwable> errorReceiver = new Receiver<Throwable>() {
        @Override
        public void accept(@NonNull Throwable value) {
            textView.setText(value.toString());
            repositories.removeUpdatable(AgeraSampleStart.this);
        }
    };

    @Override
    public void update() {
        repositories.get()
                .ifFailedSendTo(errorReceiver)
                .ifSucceededSendTo(success);
    }
}
