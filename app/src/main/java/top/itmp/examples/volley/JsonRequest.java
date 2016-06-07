package top.itmp.examples.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import top.itmp.examples.ExamplesApplication;
import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 2016/6/6.
 */
public class JsonRequest extends BaseActivity {
    private final String REQUEST_URL = "http://api.androidhive.info/contacts";
    private final String STRING_URL = "http://api.androidhive.info/volley/string_response.html";
    private final String POST_URL = "http://api.androidhive.info/volley/person_object.json";
    private final String TAG = JsonRequest.class.getSimpleName();

    @Override
    public boolean isVoiceInteraction() {
        return super.isVoiceInteraction();
    }

    private FloatingActionButton floatingActionButton;
    private ProgressDialog pDialog;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CoordinatorLayout coordinatorLayout = new CoordinatorLayout(this);
        coordinatorLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(coordinatorLayout);

        final ScrollView scrollView = new ScrollView(JsonRequest.this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final TextView textView = new TextView(JsonRequest.this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        scrollView.addView(textView);
        coordinatorLayout.addView(scrollView);

        float denisty = getResources().getDisplayMetrics().density;

        floatingActionButton = new FloatingActionButton(this);
        floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.drawable.selector_fab));
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutParams.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT | Gravity.END;
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT | Gravity.END;
        layoutParams.bottomMargin = (int)(16.0 * denisty + 0.5);
        layoutParams.rightMargin = (int)(16.0 * denisty + 0.5);
        floatingActionButton.setLayoutParams(layoutParams);
        floatingActionButton.setElevation(10.0f * denisty);
        floatingActionButton.setTranslationZ(12.0f * denisty);
        floatingActionButton.setRippleColor(ContextCompat.getColor(this, android.R.color.transparent));
        floatingActionButton.setImageResource(R.mipmap.ic_launcher);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count % 3){
                    case 0:
                        addToRequestQueue(new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        textView.setText(response.toString());
                                        scrollView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                scrollView.fullScroll(View.FOCUS_DOWN);
                                            }
                                        });
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }));
                        break;
                    case 1:
                        addToRequestQueue(new StringRequest(Request.Method.GET, STRING_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        textView.setText(response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }));
                        break;
                    case 2:
                        pDialog = new ProgressDialog(JsonRequest.this);
                        pDialog.setMessage("Loading...");
                        pDialog.show();
                        addToRequestQueue(new JsonObjectRequest(Request.Method.POST,
                                POST_URL, null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());
                                        pDialog.hide();
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                pDialog.dismiss();
                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("name", "Androidhive");
                                params.put("email", "abc@androidhive.info");
                                params.put("password", "password123");

                                return params;
                            }

                        });

                        break;
                }
                count++;
            }
        });


        coordinatorLayout.addView(floatingActionButton);

        //floatingActionButton.setBottom(0);
        //floatingActionButton.setRight(0);
        //floatingActionButton.setForegroundGravity(Gravity.BOTTOM|Gravity.BOTTOM);


        ExamplesApplication.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllRequest();
    }
}
