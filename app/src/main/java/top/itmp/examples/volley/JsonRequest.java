package top.itmp.examples.volley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import top.itmp.examples.ExamplesApplication;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 2016/6/6.
 */
public class JsonRequest extends BaseActivity {
    private final String REQUEST_URL = "http://api.androidhive.info/contacts";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExamplesApplication.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final ScrollView scrollView = new ScrollView(JsonRequest.this);
                        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        TextView textView = new TextView(JsonRequest.this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        textView.setText(response.toString());
                        scrollView.addView(textView);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                        setContentView(scrollView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }


}
