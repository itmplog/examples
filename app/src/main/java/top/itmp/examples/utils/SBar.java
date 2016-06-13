package top.itmp.examples.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by hz on 16/6/13.
 */
public class SBar {
    private static android.support.design.widget.Snackbar snackbar;

    private SBar(){}

    public static void init(Context context){
        View view;

        if(snackbar != null){
            return;
        }

        if(context instanceof Activity){
            Activity activity = (Activity) context;
            view = activity.getWindow().getDecorView().getRootView();

            snackbar = android.support.design.widget.Snackbar.make(view, "", android.support.design.widget.Snackbar.LENGTH_SHORT);
        }else{
            throw new IllegalArgumentException("The Context must be from a Activity!!");
        }
    }

    public static void setTitle(CharSequence charSequence){
        snackbar.setText(charSequence);
    }

    public static void setDuration(int duration){
        snackbar.setDuration(duration);
    }

    public static void setAction(CharSequence charSequence, View.OnClickListener onClickListener){
        snackbar.setAction(charSequence, onClickListener);
    }

    public static void setTextAndShow(CharSequence charSequence){
        snackbar.setText(charSequence)
                .show();
    }

    public static void show(){
        snackbar.show();
    }

}
